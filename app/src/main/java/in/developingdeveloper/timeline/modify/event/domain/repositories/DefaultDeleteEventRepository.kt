package `in`.developingdeveloper.timeline.modify.event.domain.repositories

import `in`.developingdeveloper.timeline.eventlist.domain.datasource.EventsDataSource
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class DefaultDeleteEventRepository @Inject constructor(
    private val eventsDataSource: EventsDataSource,
) : DeleteEventRepository {
    override suspend fun deleteEvent(eventId: String): Result<Unit> {
        return try {
            eventsDataSource.deleteEvent(eventId = eventId)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
