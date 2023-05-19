package `in`.developingdeveloper.timeline.modify.event.domain.repositories

import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.eventlist.domain.datasource.EventsDataSource
import javax.inject.Inject

class DefaultAddEventRepository @Inject constructor(
    private val eventDataSource: EventsDataSource,
) : AddEventRepository {
    override suspend fun addEvent(event: Event) {
        eventDataSource.addEvent(event)
    }
}
