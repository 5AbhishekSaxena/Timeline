package `in`.developingdeveloper.timeline.core.utils.export.excel

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.Workbook
import java.io.IOException
import javax.inject.Inject

class DefaultEventFileExporter @Inject constructor(
    @ApplicationContext
    val context: Context,
) : EventFileExporter {
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun export(fileUri: Uri, workbook: Workbook): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val contentResolver = context.contentResolver
                contentResolver.openOutputStream(fileUri)
                    .use { workbook.write(it) }

                Result.success(fileUri.path.toString())
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: IOException) {
                Result.failure(exception)
            } catch (exception: IllegalArgumentException) {
                Result.failure(exception)
            }
        }
    }
}
