package `in`.developingdeveloper.timeline.core.data.local.events

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "event_tag_cross_ref",
    primaryKeys = ["event_id", "tag_id"],
)
data class EventTagCrossRef(
    @ColumnInfo(name = "event_id")
    val eventId: String,
    @ColumnInfo(name = "tag_id", index = true)
    val tagId: String,
)
