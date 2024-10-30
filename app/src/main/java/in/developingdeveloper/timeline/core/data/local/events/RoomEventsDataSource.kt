package `in`.developingdeveloper.timeline.core.data.local.events

import `in`.developingdeveloper.timeline.core.data.local.tags.PersistableTag
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.eventlist.domain.datasource.EventsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomEventsDataSource @Inject constructor(
    private val eventDao: EventDao,
) : EventsDataSource {

    override suspend fun addEvent(event: Event) {
        val persistableEventWithTags = event.toPersistableEventsWithTags()
        eventDao.saveEventWithTags(persistableEventWithTags)
    }

    override suspend fun addEvents(events: List<Event>) {
        val persistableEventWithTags = events.map(Event::toPersistableEventsWithTags)
        eventDao.saveEventsWithTags(persistableEventWithTags)
    }

    override fun getAllEvents(): Flow<List<Event>> {
        return eventDao.getAllEvents()
            .map(List<PersistableEventWithTags>::toEvents)
    }

    override suspend fun getEventById(eventId: String): Event? {
        return eventDao.getEventById(eventId)?.toEvent()
    }

    override suspend fun updateEvent(event: Event) {
        val persistableEventWithTags = event.toPersistableEventsWithTags()
        eventDao.updateEventWithTags(persistableEventWithTags)
    }

    override suspend fun deleteEvent(eventId: String) {
        eventDao.deleteEvent(eventId = eventId)
    }
}

private fun Event.toPersistableEventsWithTags(): PersistableEventWithTags {
    val persistableEvent = this.toPersistableEvent()
    val persistableTags = this.tags.toPersistableTags()

    return PersistableEventWithTags(
        event = persistableEvent,
        tags = persistableTags,
    )
}

private fun List<PersistableEventWithTags>.toEvents(): List<Event> {
    return this.map(PersistableEventWithTags::toEvent)
}

private fun PersistableEventWithTags.toEvent(): Event {
    val event = this.event
    val tags = this.tags.toTags()
    return Event(
        id = event.id,
        title = event.title,
        tags = tags,
        date = event.date,
        createdOn = event.createdOn,
    )
}

private fun List<PersistableTag>.toTags(): List<Tag> {
    return this.map(PersistableTag::toTag)
}

private fun PersistableTag.toTag(): Tag {
    return Tag(
        id = this.id,
        label = this.label,
    )
}

private fun List<Tag>.toPersistableTags(): List<PersistableTag> {
    return this.map(Tag::toPersistableTag)
}

private fun Tag.toPersistableTag(): PersistableTag {
    return PersistableTag(
        id = this.id,
        label = this.label,
    )
}

private fun Event.toPersistableEvent(): PersistableEvent {
    return PersistableEvent(
        id = this.id,
        title = this.title,
        date = this.date,
        createdOn = this.createdOn,
    )
}
