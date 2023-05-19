package `in`.developingdeveloper.timeline.modify.event.domain.repositories

import `in`.developingdeveloper.timeline.core.domain.event.models.Event

interface UpdateEventRepository {
    suspend fun updateEvent(event: Event)
}
