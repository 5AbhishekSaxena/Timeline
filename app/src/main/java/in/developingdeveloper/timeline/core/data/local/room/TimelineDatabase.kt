package `in`.developingdeveloper.timeline.core.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import `in`.developingdeveloper.timeline.core.data.local.events.EventDao
import `in`.developingdeveloper.timeline.core.data.local.events.EventTagCrossRef
import `in`.developingdeveloper.timeline.core.data.local.events.PersistableEvent
import `in`.developingdeveloper.timeline.core.data.local.tags.PersistableTag
import `in`.developingdeveloper.timeline.core.data.local.tags.TagDao

@Database(
    entities = [PersistableEvent::class, PersistableTag::class, EventTagCrossRef::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(LocalDateTimeTypeConvertor::class)
abstract class TimelineDatabase : RoomDatabase() {

    abstract fun tagDao(): TagDao
    abstract fun eventDao(): EventDao

    companion object {
        private const val DB_NAME = "timeline-database"

        fun build(context: Context): TimelineDatabase {
            return Room
                .databaseBuilder(
                    context,
                    TimelineDatabase::class.java,
                    DB_NAME,
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
