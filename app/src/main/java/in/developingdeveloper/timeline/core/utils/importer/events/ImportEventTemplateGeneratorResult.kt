package `in`.developingdeveloper.timeline.core.utils.importer.events

sealed class ImportEventTemplateGeneratorResult<out T> {
    data class StatusUpdate(val status: String) : ImportEventTemplateGeneratorResult<Nothing>()
    data class Success<T>(val data: T) : ImportEventTemplateGeneratorResult<T>()
    data class Failure(
        val error: Throwable,
        val source: Source = Source.UNKNOWN,
    ) : ImportEventTemplateGeneratorResult<Nothing>() {
        enum class Source {
            UNKNOWN, WRITER, EXPORTER
        }
    }
}
