package `in`.developingdeveloper.timeline.core.data.local.events

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "events")
data class PersistableEvent(
    @PrimaryKey
    @ColumnInfo(name = "event_id")
    val id: String,
    val title: String,
    val date: LocalDateTime,
    val createdOn: LocalDateTime,
)
