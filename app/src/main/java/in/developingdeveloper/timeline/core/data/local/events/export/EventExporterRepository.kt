package `in`.developingdeveloper.timeline.core.data.local.events.export

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class EventExporterRepository @Inject constructor(
    @ApplicationContext
    private val context: Context,
) {
    suspend fun export(destinationFolderUri: Uri): Result<Unit> = withContext(Dispatchers.IO) {
        val contentResolver = context.contentResolver

        val documentUri = DocumentsContract.buildDocumentUriUsingTree(
            destinationFolderUri,
            DocumentsContract.getTreeDocumentId(destinationFolderUri),
        )

        return@withContext try {
            val newFileUri = DocumentsContract.createDocument(
                contentResolver,
                documentUri,
                "text/plain",
                "event.txt",
            )

            if (newFileUri == null) return@withContext Result.failure(Exception("File uri is null"))

            contentResolver.openOutputStream(newFileUri)?.use { out ->
                val content = "Hello World"
                out.write(content.toByteArray())
                out.flush()
            }
            Result.success(Unit)
        } catch (exception: IOException) {
            Result.failure(exception)
        }
    }
}
