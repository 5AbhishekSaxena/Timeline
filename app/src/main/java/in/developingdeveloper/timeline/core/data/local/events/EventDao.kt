package `in`.developingdeveloper.timeline.core.data.local.events

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Transaction
    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<PersistableEventWithTags>>

    @Transaction
    @Query("SELECT * FROM events WHERE event_id = :eventId")
    suspend fun getEventById(eventId: String): PersistableEventWithTags?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvent(event: PersistableEvent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEventWithTag(event: EventTagCrossRef)

    @Transaction
    suspend fun addEventWithTags(eventWithTags: PersistableEventWithTags) {
        val (event, tags) = eventWithTags
        addEvent(event)

        tags
            .map {
                EventTagCrossRef(event.id, it.id)
            }.forEach {
                addEventWithTag(it)
            }
    }

    @Update
    suspend fun updateEvent(event: PersistableEvent)

    @Delete
    suspend fun deleteEventWithTag(event: EventTagCrossRef)

    @Transaction
    @Suppress("TooGenericExceptionThrown")
    suspend fun updateEventWithTags(eventWithTags: PersistableEventWithTags) {
        val (event, tags) = eventWithTags

        val existingEvent =
            getEventById(event.id) ?: throw Exception("Event with id ${event.id} doesn't exist.")

        updateEvent(event)

        val addedTags = tags.minus(existingEvent.tags.toSet())
        val removedTags = existingEvent.tags.minus(tags.toSet())

        addedTags
            .map {
                EventTagCrossRef(event.id, it.id)
            }.forEach {
                addEventWithTag(it)
            }

        removedTags
            .map {
                EventTagCrossRef(event.id, it.id)
            }.forEach {
                deleteEventWithTag(it)
            }
    }
}
