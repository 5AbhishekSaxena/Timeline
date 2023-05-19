package `in`.developingdeveloper.timeline.modify.event.domain.usecases

import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.eventlist.domain.repositories.EventListRepository
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class DefaultGetEventByIdUseCase @Inject constructor(
    private val eventListRepository: EventListRepository,
) : GetEventByIdUseCase {
    override suspend fun invoke(eventId: String): Result<Event> {
        return try {
            val event = eventListRepository.getEventById(eventId)
            Result.success(event)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
