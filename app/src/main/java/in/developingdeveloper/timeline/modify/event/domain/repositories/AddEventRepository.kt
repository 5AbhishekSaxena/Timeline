package `in`.developingdeveloper.timeline.modify.event.domain.repositories

import `in`.developingdeveloper.timeline.core.domain.event.models.Event

interface AddEventRepository {
    suspend fun addEvent(event: Event)
    suspend fun addEvents(events: List<Event>)
}
