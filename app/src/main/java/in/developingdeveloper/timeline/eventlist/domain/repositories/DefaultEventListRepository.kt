package `in`.developingdeveloper.timeline.eventlist.domain.repositories

import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.eventlist.domain.datasource.EventsDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultEventListRepository @Inject constructor(
    private val eventsDataSource: EventsDataSource,
) : EventListRepository {
    override fun getAllEvents(): Flow<List<Event>> {
        return eventsDataSource.getAllEvents()
    }

    @Suppress("TooGenericExceptionThrown")
    override suspend fun getEventById(eventId: String): Event {
        return eventsDataSource.getEventById(eventId) ?: throw Exception("Event with $eventId not found.")
    }
}
