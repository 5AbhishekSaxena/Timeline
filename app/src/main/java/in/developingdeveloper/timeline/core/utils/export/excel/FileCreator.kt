package `in`.developingdeveloper.timeline.core.utils.export.excel

import android.net.Uri

interface FileCreator {
    suspend fun create(
        destinationDirectory: Uri,
        fileName: String,
        mimeType: String,
    ): Result<String>
}
