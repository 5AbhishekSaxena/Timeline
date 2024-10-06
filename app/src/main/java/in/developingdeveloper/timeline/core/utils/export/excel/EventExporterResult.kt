package `in`.developingdeveloper.timeline.core.utils.export.excel

sealed class EventExporterResult<out T> {
    data class StatusUpdate(val status: String) : EventExporterResult<Nothing>()
    data class Success<T>(val data: T) : EventExporterResult<T>()
    data class Failure(
        val error: Throwable,
        val source: Source = Source.UNKNOWN,
    ) : EventExporterResult<Nothing>() {
        enum class Source {
            UNKNOWN, WRITER, EXPORTER
        }
    }
}
