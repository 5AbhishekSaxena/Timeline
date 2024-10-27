package `in`.developingdeveloper.timeline.core.data.local.events

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import `in`.developingdeveloper.timeline.core.data.local.tags.PersistableTag
import kotlinx.coroutines.flow.Flow

@Dao
@SuppressWarnings("TooManyFunctions")
interface EventDao {

    @Transaction
    @Query("SELECT * FROM events ORDER BY date DESC")
    fun getAllEvents(): Flow<List<PersistableEventWithTags>>

    @Transaction
    @Query("SELECT * FROM events WHERE event_id = :eventId")
    suspend fun getEventById(eventId: String): PersistableEventWithTags?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEvent(event: PersistableEvent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEventWithTag(event: EventTagCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEventWithTags(events: List<EventTagCrossRef>)

    @Transaction
    suspend fun saveEventWithTags(eventWithTags: PersistableEventWithTags) {
        val (event, tags) = eventWithTags
        saveEvent(event)

        saveEventTagCrossRef(tags, event)
    }

    @Transaction
    suspend fun saveEventsWithTags(eventWithTags: List<PersistableEventWithTags>) {
        eventWithTags.forEach { saveEventWithTags(it) }
    }

    @Update
    suspend fun updateEvent(event: PersistableEvent)

    @Delete
    suspend fun deleteEventWithTag(event: EventTagCrossRef)

    @Delete
    suspend fun deleteEventWithTags(events: List<EventTagCrossRef>)

    @Transaction
    suspend fun deleteEvent(eventId: String) {
        deleteEventCrossRef(eventId)
        deleteEventById(eventId)
    }

    @Query("DELETE FROM event_tag_cross_ref WHERE event_id = :eventId")
    suspend fun deleteEventCrossRef(eventId: String)

    @Query("DELETE FROM events WHERE event_id = :eventId")
    suspend fun deleteEventById(eventId: String)

    @Transaction
    @Suppress("TooGenericExceptionThrown")
    suspend fun updateEventWithTags(eventWithTags: PersistableEventWithTags) {
        val (event, tags) = eventWithTags

        val existingEvent =
            getEventById(event.id) ?: throw Exception("Event with id ${event.id} doesn't exist.")

        updateEvent(event)

        val addedTags = tags.minus(existingEvent.tags.toSet())
        val removedTags = existingEvent.tags.minus(tags.toSet())

        saveEventTagCrossRef(addedTags, event)
        deleteEventTagCrossRef(removedTags, event)
    }

    private suspend fun saveEventTagCrossRef(
        tags: List<PersistableTag>,
        event: PersistableEvent,
    ) {
        tags
            .map { EventTagCrossRef(event.id, it.id) }
            .let { saveEventWithTags(it) }
    }

    private suspend fun deleteEventTagCrossRef(
        tags: List<PersistableTag>,
        event: PersistableEvent,
    ) {
        tags
            .map { EventTagCrossRef(event.id, it.id) }
            .let { deleteEventWithTags(it) }
    }
}
