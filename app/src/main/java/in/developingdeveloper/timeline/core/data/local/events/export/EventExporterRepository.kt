package `in`.developingdeveloper.timeline.core.data.local.events.export

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.io.IOException
import javax.inject.Inject

class EventExporterRepository @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val eventExporter: ExcelEventsExporter,
) {
    suspend fun export(destinationFolderUri: Uri, events: List<Event>): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                checkIfDestinationDirectoryExists(destinationFolderUri)

                eventExporter.export(destinationFolderUri, events)
                    .collect {
                        Log.e(javaClass.name, "export, result: $it")
                    }
                Result.success(Unit)
            } catch (exception: IOException) {
                Result.failure(exception)
            }
        }
    }

    private fun checkIfDestinationDirectoryExists(destinationFolderUri: Uri) {
        val documentFile = DocumentFile.fromTreeUri(context, destinationFolderUri)
        val exists = documentFile?.exists() ?: false
        if (!exists) throw FileNotFoundException("Destination folder changed or removed.")
    }
}
