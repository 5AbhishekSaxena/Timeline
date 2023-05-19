package `in`.developingdeveloper.timeline.core.exceptions

open class TimelineException : Exception {

    private val defaultMessage = "Something went wrong."

    override val message: String
        get() {
            return super.message ?: defaultMessage
        }

    constructor(message: String? = null) : super(message)

    constructor(throwable: Throwable) : super(throwable.message, throwable)
}
