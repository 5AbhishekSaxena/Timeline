package `in`.developingdeveloper.timeline.core.data.local.room

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeTypeConvertor {

    @TypeConverter
    fun toLocalDateTime(value: String): LocalDateTime {
        return LocalDateTime.parse(value)
    }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime): String {
        return value.toString()
    }
}
