package `in`.developingdeveloper.timeline.eventlist.ui.models

data class EventListViewState(
    val events: List<UIEventListItem>,
    val loading: Boolean,
    val alertMessage: String?,
    val errorMessage: String?,
    val isExportingEvents: Boolean,
    val requestForEventExportDestination: Boolean,
    val exportStatusMessage: String?,
) {
    companion object {
        val Initial = EventListViewState(
            events = emptyList(),
            loading = false,
            alertMessage = null,
            errorMessage = null,
            isExportingEvents = false,
            requestForEventExportDestination = false,
            exportStatusMessage = null,
        )
    }
}
