package `in`.developingdeveloper.timeline.modify.event.domain.usecases

import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.modify.event.domain.exceptions.ModifyEventException
import `in`.developingdeveloper.timeline.modify.event.domain.repositories.AddEventRepository
import `in`.developingdeveloper.timeline.modify.event.domain.repositories.UpdateEventRepository
import java.time.LocalDateTime
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught", "TooGenericExceptionThrown")
class DefaultModifyEventUseCase @Inject constructor(
    private val addEventRepository: AddEventRepository,
    private val updateEventRepository: UpdateEventRepository,
) : ModifyEventUseCase {
    override suspend fun invoke(event: Event, isNewEvent: Boolean): Result<Unit> {
        return try {
            validateEvent(event)

            if (isNewEvent) {
                addEventRepository.addEvent(event)
            } else {
                updateEventRepository.updateEvent(event)
            }

            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    private fun validateEvent(event: Event) {
        if (event.title.isBlank()) {
            throw ModifyEventException.InvalidTitleException("Title is required.")
        }

        if (event.date.isAfter(LocalDateTime.now())) {
            throw ModifyEventException.InvalidOccurredOnException("Event must be in past.")
        }
    }
}
