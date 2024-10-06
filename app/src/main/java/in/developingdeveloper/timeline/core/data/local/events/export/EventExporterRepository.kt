package `in`.developingdeveloper.timeline.core.data.local.events.export

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.utils.export.excel.EventExporterResult
import kotlinx.coroutines.flow.Flow
import java.io.FileNotFoundException
import javax.inject.Inject

class EventExporterRepository @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val eventExporter: ExcelEventsExporter,
) {
    fun export(destinationFolderUri: Uri, events: List<Event>): Flow<EventExporterResult<String>> {
        checkIfDestinationDirectoryExists(destinationFolderUri)
        return eventExporter.export(destinationFolderUri, events)
    }

    private fun checkIfDestinationDirectoryExists(destinationFolderUri: Uri) {
        val documentFile = DocumentFile.fromTreeUri(context, destinationFolderUri)
        val exists = documentFile?.exists() ?: false
        if (!exists) throw FileNotFoundException("Destination folder changed or removed.")
    }
}
