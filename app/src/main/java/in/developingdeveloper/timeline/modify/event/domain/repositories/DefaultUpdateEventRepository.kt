package `in`.developingdeveloper.timeline.modify.event.domain.repositories

import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.eventlist.domain.datasource.EventsDataSource
import javax.inject.Inject

class DefaultUpdateEventRepository @Inject constructor(
    private val eventsDataSource: EventsDataSource,
) : UpdateEventRepository {
    override suspend fun updateEvent(event: Event) {
        eventsDataSource.updateEvent(event)
    }
}
