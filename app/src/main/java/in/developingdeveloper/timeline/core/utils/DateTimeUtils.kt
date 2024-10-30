package `in`.developingdeveloper.timeline.core.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val UTC = "UTC"
private val zoneId = ZoneId.of(UTC)
private const val UI_DATE_FORMAT = "dd MMMM yyyy"
private val uiDateTimeFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern(UI_DATE_FORMAT)

private const val COMPLETE_DATA_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
private val completeDataDateTimeFormatter = DateTimeFormatter.ofPattern(COMPLETE_DATA_DATE_FORMAT)

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant
        .ofEpochMilli(this)
        .atZone(zoneId)
        .toLocalDateTime()
}

fun Long.toLocalDate(): LocalDate {
    return this.toLocalDateTime().toLocalDate()
}

fun LocalDateTime.toEpochMilli(): Long {
    return this.atZone(zoneId).toInstant().toEpochMilli()
}

fun LocalDate.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.of(this, LocalTime.MIDNIGHT)
}

fun LocalDateTime.formatDateForUI(): String {
    return this.format(uiDateTimeFormatter) ?: this.toString()
}

fun LocalDateTime.formatDateTimeWithCompleteData(): String {
    return this.format(completeDataDateTimeFormatter) ?: this.toString()
}

fun String.parseLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this, completeDataDateTimeFormatter)
}
