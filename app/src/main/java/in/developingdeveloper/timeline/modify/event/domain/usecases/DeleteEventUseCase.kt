package `in`.developingdeveloper.timeline.modify.event.domain.usecases

interface DeleteEventUseCase {

    suspend operator fun invoke(eventId: String): Result<Unit>
}
