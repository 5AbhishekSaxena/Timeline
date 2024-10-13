package `in`.developingdeveloper.timeline.core.utils.importer.events

import android.net.Uri
import `in`.developingdeveloper.timeline.core.utils.export.excel.EventFileExporter
import `in`.developingdeveloper.timeline.core.utils.export.excel.ExcelFileWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.apache.poi.ss.usermodel.Workbook
import javax.inject.Inject

class ImportEventTemplateGenerator @Inject constructor(
    private val writer: ExcelFileWriter,
    private val exporter: EventFileExporter,
) {
    fun generate(fileUri: Uri) = flow(
        block = {
            emit(ImportEventTemplateGeneratorResult.StatusUpdate("Mapping template content."))

            val headings = arrayOf(
                "Title",
                "Occurred On(YYYY/MM/DD)",
                "Tags(separate tags using ',')",
            )

            emit(
                ImportEventTemplateGeneratorResult
                    .StatusUpdate("Writing template content to excel file."),
            )

            val excelFileWriterResult = writer.write(mapOf("headings" to headings))
            handleExcelFileWriterResult(fileUri, excelFileWriterResult)
        },
    ).flowOn(Dispatchers.IO)

    private suspend fun FlowCollector<ImportEventTemplateGeneratorResult<String>>.handleExcelFileWriterResult(
        fileUri: Uri,
        excelFileWriterResult: Result<Workbook>,
    ) {
        excelFileWriterResult.fold(
            onSuccess = { handleExcelFileWriterSuccess(fileUri, it) },
            onFailure = { handleExcelFileWriterFailure(it) },
        )
    }

    private suspend fun FlowCollector<ImportEventTemplateGeneratorResult<String>>.handleExcelFileWriterSuccess(
        fileUri: Uri,
        workbook: Workbook,
    ) {
        val excelFileExporterResult = exporter.export(fileUri, workbook)
        handleExcelFileExporterResult(excelFileExporterResult)
    }

    private suspend fun FlowCollector<ImportEventTemplateGeneratorResult<String>>.handleExcelFileExporterResult(
        excelFileExporterResult: Result<String>,
    ) {
        excelFileExporterResult.fold(
            onSuccess = { emit(ImportEventTemplateGeneratorResult.Success(it)) },
            onFailure = {
                emit(
                    ImportEventTemplateGeneratorResult.Failure(
                        it,
                        source = ImportEventTemplateGeneratorResult.Failure.Source.EXPORTER,
                    ),
                )
            },
        )
    }

    private suspend fun FlowCollector<ImportEventTemplateGeneratorResult<String>>.handleExcelFileWriterFailure(
        error: Throwable,
    ) {
        emit(
            ImportEventTemplateGeneratorResult.Failure(
                error,
                source = ImportEventTemplateGeneratorResult.Failure.Source.WRITER,
            ),
        )
    }
}
