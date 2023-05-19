package `in`.developingdeveloper.timeline.core.data.local.tags

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class PersistableTag(
    @PrimaryKey
    @ColumnInfo(name = "tag_id")
    val id: String,
    val label: String,
)
