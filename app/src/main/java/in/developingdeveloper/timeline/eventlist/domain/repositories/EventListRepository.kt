package `in`.developingdeveloper.timeline.eventlist.domain.repositories

import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import kotlinx.coroutines.flow.Flow

interface EventListRepository {
    fun getAllEvents(): Flow<List<Event>>
    suspend fun getEventById(eventId: String): Event
}
