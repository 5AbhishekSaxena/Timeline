package `in`.developingdeveloper.timeline.modify.event.domain.repositories

interface DeleteEventRepository {
    suspend fun deleteEvent(eventId: String): Result<Unit>
}
