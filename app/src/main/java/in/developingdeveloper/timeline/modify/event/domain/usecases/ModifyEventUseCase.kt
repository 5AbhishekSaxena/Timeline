package `in`.developingdeveloper.timeline.modify.event.domain.usecases

import `in`.developingdeveloper.timeline.core.domain.event.models.Event

interface ModifyEventUseCase {
    suspend fun invoke(event: Event, isNewEvent: Boolean): Result<Unit>
}
