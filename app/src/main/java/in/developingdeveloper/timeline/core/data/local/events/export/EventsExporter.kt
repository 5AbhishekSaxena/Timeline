package `in`.developingdeveloper.timeline.core.data.local.events.export

import android.net.Uri
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.utils.export.excel.EventExporterResult
import kotlinx.coroutines.flow.Flow

interface EventsExporter {
    fun export(destination: Uri, events: List<Event>): Flow<EventExporterResult<String>>
}
