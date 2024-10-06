package `in`.developingdeveloper.timeline.core.data.local.events.export

import android.net.Uri
import android.util.Log
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.utils.export.excel.ExcelEventsExporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class EventExporterRepository @Inject constructor(
    private val eventExporter: ExcelEventsExporter,
) {
    suspend fun export(destinationFolderUri: Uri, events: List<Event>): Result<Unit> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
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
}
