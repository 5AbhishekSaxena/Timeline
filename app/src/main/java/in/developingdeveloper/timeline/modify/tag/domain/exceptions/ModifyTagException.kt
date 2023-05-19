package `in`.developingdeveloper.timeline.modify.tag.domain.exceptions

import `in`.developingdeveloper.timeline.core.exceptions.TimelineException

sealed class ModifyTagException(message: String?) : TimelineException(message) {

    class InvalidIdException(message: String?) : ModifyTagException(message)

    class InvalidLabelException(message: String?) : ModifyTagException(message)
}
