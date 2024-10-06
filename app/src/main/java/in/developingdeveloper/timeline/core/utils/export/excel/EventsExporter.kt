package `in`.developingdeveloper.timeline.core.utils.export.excel

import android.net.Uri
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import kotlinx.coroutines.flow.Flow

interface EventsExporter {
    fun export(destination: Uri, events: List<Event>): Flow<EventExporterResult<String>>
}
