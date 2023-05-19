package `in`.developingdeveloper.timeline.core.exceptions

import org.junit.Assert.assertEquals
import org.junit.Test

class TimelineExceptionTest {

    @Test
    fun getDefaultMessageIfMessageIsNotProvided() {
        val exception = TimelineException()

        val result = exception.message

        val expected = "Something went wrong."

        assertEquals(expected, result)
    }

    @Test
    fun getMessageIfMessageIsProvided() {
        val message = "Failed to perform operation."

        val exception = TimelineException(message)

        val result = exception.message

        assertEquals(message, result)
    }

    @Test
    fun getDefaultMessageIfMessageIsNotProvidedWithThrowable() {
        val exception = TimelineException(Throwable())

        val result = exception.message

        val expected = "Something went wrong."

        assertEquals(expected, result)
    }

    @Test
    fun getMessageIfMessageIsProvidedWithThrowable() {
        val message = "Failed to perform operation."

        val exception = TimelineException(Throwable(message))

        val result = exception.message

        assertEquals(message, result)
    }
}
