package `in`.developingdeveloper.timeline.eventlist.domain.datasource

import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import kotlinx.coroutines.flow.Flow

interface EventsDataSource {
    fun getAllEvents(): Flow<List<Event>>
    suspend fun getEventById(eventId: String): Event?
    suspend fun addEvent(event: Event)
    suspend fun updateEvent(event: Event)
}
