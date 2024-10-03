package `in`.developingdeveloper.timeline.eventlist.ui.models

data class EventListViewState(
    val events: List<EventListItem.UIEvent>,
    val loading: Boolean,
    val errorMessage: String?,
) {
    companion object {
        val Initial = EventListViewState(
            events = emptyList(),
            loading = false,
            errorMessage = null,
        )
    }
}
