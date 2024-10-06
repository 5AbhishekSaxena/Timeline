package `in`.developingdeveloper.timeline.eventlist.domain.usescases

import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.utils.export.excel.EventExporterResult
import kotlinx.coroutines.flow.Flow

interface EventExporterUseCase {

    operator fun invoke(events: List<Event>): Flow<EventExporterResult<String>>
}
