package `in`.developingdeveloper.timeline.core.data.local.events.export

import android.net.Uri
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.utils.export.excel.EventExporterResult
import `in`.developingdeveloper.timeline.core.utils.export.excel.EventFileExporter
import `in`.developingdeveloper.timeline.core.utils.export.excel.ExcelFileWriter
import `in`.developingdeveloper.timeline.core.utils.formatDateTimeWithCompleteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import org.apache.poi.ss.usermodel.Workbook
import javax.inject.Inject

class ExcelEventsExporter @Inject constructor(
    private val writer: ExcelFileWriter,
    private val exporter: EventFileExporter,
) : EventsExporter {
    override fun export(destination: Uri, events: List<Event>): Flow<EventExporterResult<String>> =
        flow {
            emit(EventExporterResult.StatusUpdate("Mapping events"))

            val headings = arrayOf(
                "S.No.",
                "Id",
                "Title",
                "Occurred On",
                "Tags",
            )

            val eventsMap = mutableMapOf(
                "headings" to headings,
            )

            events.forEachIndexed { index, event ->
                eventsMap[index.toString()] = event.mapToArray(index)
            }

            emit(EventExporterResult.StatusUpdate("Writing events to Excel"))

            val excelFileWriterResult = writer.write(eventsMap)
            handleExcelFileWriterResult(destination, excelFileWriterResult)
        }

    private suspend fun FlowCollector<EventExporterResult<String>>.handleExcelFileWriterResult(
        destination: Uri,
        excelFileWriterResult: Result<Workbook>,
    ) {
        excelFileWriterResult.fold(
            onSuccess = { handleExcelFileWriterSuccess(destination, it) },
            onFailure = { handleExcelFileWriterFailure(it) },
        )
    }

    private suspend fun FlowCollector<EventExporterResult<String>>.handleExcelFileWriterSuccess(
        destination: Uri,
        workbook: Workbook,
    ) {
        emit(EventExporterResult.StatusUpdate("Saving events to Excel"))
        val excelFileExporterResult = exporter.export(destination, workbook)
        handleExcelFileExporterResult(excelFileExporterResult)
    }

    private suspend fun FlowCollector<EventExporterResult<String>>.handleExcelFileExporterResult(
        excelFileExporterResult: Result<String>,
    ) {
        excelFileExporterResult.fold(
            onSuccess = {
                emit(EventExporterResult.Success(it))
            },
            onFailure = {
                emit(
                    EventExporterResult.Failure(
                        it,
                        source = EventExporterResult.Failure.Source.EXPORTER,
                    ),
                )
            },
        )
    }

    private suspend fun FlowCollector<EventExporterResult<String>>.handleExcelFileWriterFailure(
        error: Throwable,
    ) {
        emit(
            EventExporterResult.Failure(
                error,
                source = EventExporterResult.Failure.Source.WRITER,
            ),
        )
    }
}

private fun Event.mapToArray(index: Int): Array<String> {
    return arrayOf(
        (index + 1).toString(),
        id,
        title,
        date.formatDateTimeWithCompleteData(),
        tags.joinToString(", ") { it.label },
    )
}
