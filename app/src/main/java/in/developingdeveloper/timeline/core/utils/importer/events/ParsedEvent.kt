package `in`.developingdeveloper.timeline.core.utils.importer.events

data class ParsedEvent(
    val serialNumber: String,
    val id: String,
    val title: String,
    val occurredOn: String,
    val tags: String,
)
