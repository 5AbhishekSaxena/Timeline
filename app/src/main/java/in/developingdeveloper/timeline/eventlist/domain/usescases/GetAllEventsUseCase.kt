package `in`.developingdeveloper.timeline.eventlist.domain.usescases

import `in`.developingdeveloper.timeline.eventlist.domain.models.Event
import kotlinx.coroutines.flow.Flow

interface GetAllEventsUseCase {
    operator fun invoke(): Flow<Result<List<Event>>>
}
