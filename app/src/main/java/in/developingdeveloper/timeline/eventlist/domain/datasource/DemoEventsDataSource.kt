package `in`.developingdeveloper.timeline.eventlist.domain.datasource

import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import javax.inject.Inject

class DemoEventsDataSource @Inject constructor() : EventsDataSource {

    private val events = listOf(
        Event(
            id = "1",
            title = "Started new app - Timeline.",
            tags = listOf(
                Tag("1", "Android"),
                Tag("2", "Kotlin"),
            ),
            date = LocalDateTime.now(),
            createdOn = LocalDateTime.now(),
        ),
        Event(
            id = "2",
            title = "Demo event",
            tags = emptyList(),
            date = LocalDateTime.now(),
            createdOn = LocalDateTime.now(),
        ),
    )

    override fun getAllEvents(): Flow<List<Event>> = flowOf(events)

    override suspend fun getEventById(eventId: String): Event? {
        return events.firstOrNull { it.id == eventId }
    }

    override suspend fun addEvent(event: Event) = Unit

    override suspend fun updateEvent(event: Event) = Unit
}
