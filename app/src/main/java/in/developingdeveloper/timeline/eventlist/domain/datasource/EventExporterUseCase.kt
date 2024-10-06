package `in`.developingdeveloper.timeline.eventlist.domain.datasource

import `in`.developingdeveloper.timeline.core.domain.event.models.Event

interface EventExporterUseCase {

    suspend operator fun invoke(events: List<Event>): Result<Unit>
}
