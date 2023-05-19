package `in`.developingdeveloper.timeline.modify.event.domain.exceptions

import `in`.developingdeveloper.timeline.core.exceptions.TimelineException

sealed class ModifyEventException(message: String? = null) : TimelineException(message) {

    class InvalidTitleException(message: String?) : ModifyEventException(message)
    class InvalidOccurredOnException(message: String?) : ModifyEventException(message)
}
