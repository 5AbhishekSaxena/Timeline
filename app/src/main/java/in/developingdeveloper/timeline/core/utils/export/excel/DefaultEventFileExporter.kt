package `in`.developingdeveloper.timeline.core.utils.export.excel

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.Workbook
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DefaultEventFileExporter @Inject constructor(
    @ApplicationContext
    val context: Context,
) : EventFileExporter {
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun export(destination: Uri, workbook: Workbook): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val contentResolver = context.contentResolver

                val fileName = generateFileName()

                val destinationFolderUri = DocumentsContract.buildDocumentUriUsingTree(
                    destination,
                    DocumentsContract.getTreeDocumentId(destination),
                )

                val newFileUri = DocumentsContract.createDocument(
                    contentResolver,
                    destinationFolderUri,
                    EXCEL_MIME_TYPE,
                    fileName,
                )

                if (newFileUri == null) return@withContext Result.failure(Exception("File uri is null."))

                contentResolver.openOutputStream(newFileUri)
                    .use { workbook.write(it) }

                Result.success(newFileUri.path.toString())
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: IOException) {
                Result.failure(exception)
            } catch (exception: IllegalArgumentException) {
                Result.failure(exception)
            }
        }
    }

    private fun generateFileName(): String {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss")
        return "events-${dateTimeFormatter.format(LocalDateTime.now())}.xlsx"
    }

    companion object {
        private const val EXCEL_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    }
}
