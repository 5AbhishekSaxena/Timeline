package `in`.developingdeveloper.timeline.core.utils.export.excel

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class SharedStorageFileCreator @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : FileCreator {
    override suspend fun create(
        destinationDirectory: Uri,
        fileName: String,
        mimeType: String,
    ): Result<String> {
        return try {
            val contentResolver = context.contentResolver

            val destinationFolderUri = DocumentsContract.buildDocumentUriUsingTree(
                destinationDirectory,
                DocumentsContract.getTreeDocumentId(destinationDirectory),
            )

            val newFileUri = DocumentsContract.createDocument(
                contentResolver,
                destinationFolderUri,
                mimeType,
                fileName,
            )

            if (newFileUri == null) return Result.failure(Exception("File uri is null."))

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
