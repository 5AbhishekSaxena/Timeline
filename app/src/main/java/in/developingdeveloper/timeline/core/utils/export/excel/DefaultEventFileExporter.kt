package `in`.developingdeveloper.timeline.core.utils.export.excel

import android.net.Uri
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DefaultEventFileExporter @Inject constructor() : EventFileExporter {
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun export(destination: Uri, workbook: Workbook): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val rootPath = destination.path
                    ?: throw IllegalArgumentException("Destination root path is null.")

                val root = File(rootPath)

                val fileName = generateFileName()
                val excelOutputFilePath = File(root, fileName)

                FileOutputStream(excelOutputFilePath)
                    .use { workbook.write(it) }

                Result.success(excelOutputFilePath.absoluteFile.toString())
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
}
