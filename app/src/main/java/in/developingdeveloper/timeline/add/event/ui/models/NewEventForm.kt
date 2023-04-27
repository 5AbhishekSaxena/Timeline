package `in`.developingdeveloper.timeline.add.event.ui.models

import `in`.developingdeveloper.timeline.taglist.ui.models.UITag
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class NewEventForm(
    val title: String,
    val titleErrorMessage: String?,
    val tags: Set<UITag>,
    val occurredOn: LocalDateTime,
    val occurredOnErrorMessage: String?,
) {
    companion object {
        val Initial = NewEventForm(
            title = "",
            titleErrorMessage = null,
            tags = emptySet(),
            occurredOn = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT),
            occurredOnErrorMessage = null,
        )
    }
}
