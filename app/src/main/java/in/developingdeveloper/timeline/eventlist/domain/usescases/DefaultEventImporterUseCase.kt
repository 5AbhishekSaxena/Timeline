package `in`.developingdeveloper.timeline.eventlist.domain.usescases

import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.core.domain.tags.repositories.TagRepository
import `in`.developingdeveloper.timeline.core.utils.generateRandomUUID
import `in`.developingdeveloper.timeline.core.utils.importer.events.ExcelParser
import `in`.developingdeveloper.timeline.core.utils.importer.events.ParsedEvent
import `in`.developingdeveloper.timeline.core.utils.parseLocalDateTime
import `in`.developingdeveloper.timeline.modify.event.domain.repositories.AddEventRepository
import `in`.developingdeveloper.timeline.modify.tag.domain.repositories.AddTagRepository
import java.time.LocalDateTime
import javax.inject.Inject

class DefaultEventImporterUseCase @Inject constructor(
    private val excelParser: ExcelParser,
    private val addEventRepository: AddEventRepository,
    private val tagRepository: TagRepository,
    private val addTagRepository: AddTagRepository,
) : EventImporterUseCase {
    override suspend fun invoke(fileUri: String) {
        val parsedEvents = excelParser.parse(fileUri)

        val parsedTagLabels = parsedEvents
            .asSequence()
            .map(ParsedEvent::tags)
            .filter { it.isNotBlank() }
            .map { it.split(",") }
            .flatten()
            .filter { it.isNotBlank() }
            .map { it.trim() }
            .distinct()
            .toList()

        val savedTags = tagRepository
            .getTagsByLabels(parsedTagLabels)
            .associateBy { it.label }

        val newTags = parsedTagLabels
            .filterNot { savedTags.containsKey(it) }
            .map { Tag(id = generateRandomUUID(), label = it) }
            .also { addTagRepository.addTags(it) }

        val allTags = savedTags + newTags.associateBy { it.label }

        addEventRepository.addEvents(parsedEvents.toEvents(allTags))
    }
}

private fun List<ParsedEvent>.toEvents(savedTags: Map<String, Tag>): List<Event> {
    return this.map { it.toEvent(savedTags) }
}

private fun ParsedEvent.toEvent(savedTags: Map<String, Tag>): Event {
    return Event(
        id = this.id,
        title = this.title,
        tags = this.tags.toTags(savedTags),
        date = this.occurredOn.parseLocalDateTime(),
        createdOn = LocalDateTime.now(),
    )
}

private fun String.toTags(savedTags: Map<String, Tag>): List<Tag> {
    return this.split(",")
        .filter { it.isNotBlank() }
        .map { it.trim() }
        .map {
            val savedTag = savedTags[it] ?: throw IllegalArgumentException("Tag not found: '$it'.")
            Tag(savedTag.id, savedTag.label)
        }
}
