package `in`.developingdeveloper.timeline.eventlist.ui.models

import java.time.LocalDateTime

sealed class UIEventListItem {

    data class Event(
        val id: String,
        val title: String,
        val tags: List<String>,
        val date: LocalDateTime,
        val createdOn: LocalDateTime,
    ) : UIEventListItem()
}
