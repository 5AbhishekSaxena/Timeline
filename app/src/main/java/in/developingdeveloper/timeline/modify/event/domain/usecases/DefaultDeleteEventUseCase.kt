package `in`.developingdeveloper.timeline.modify.event.domain.usecases

import `in`.developingdeveloper.timeline.modify.event.domain.repositories.DeleteEventRepository
import javax.inject.Inject

class DefaultDeleteEventUseCase @Inject constructor(
    private val deleteEventRepository: DeleteEventRepository,
) : DeleteEventUseCase {
    override suspend fun invoke(eventId: String): Result<Unit> {
        return deleteEventRepository.deleteEvent(eventId)
    }
}
