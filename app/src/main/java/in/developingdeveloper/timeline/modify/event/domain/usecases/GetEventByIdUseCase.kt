package `in`.developingdeveloper.timeline.modify.event.domain.usecases

import `in`.developingdeveloper.timeline.core.domain.event.models.Event

interface GetEventByIdUseCase {
    suspend operator fun invoke(eventId: String): Result<Event>
}
