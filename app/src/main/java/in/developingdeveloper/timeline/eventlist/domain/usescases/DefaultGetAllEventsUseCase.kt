package `in`.developingdeveloper.timeline.eventlist.domain.usescases

import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.eventlist.domain.repositories.EventListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultGetAllEventsUseCase @Inject constructor(
    private val eventListRepository: EventListRepository,
) : GetAllEventsUseCase {
    override fun invoke(): Flow<Result<List<Event>>> {
        return eventListRepository.getAllEvents()
            .map {
                Result.success(it)
            }
            .catch {
                emit(Result.failure(it))
            }
    }
}
