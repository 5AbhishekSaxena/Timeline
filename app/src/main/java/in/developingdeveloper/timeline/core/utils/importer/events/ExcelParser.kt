package `in`.developingdeveloper.timeline.core.utils.importer.events

interface ExcelParser {
    suspend fun parse(fileUri: String): List<ParsedEvent>
}
