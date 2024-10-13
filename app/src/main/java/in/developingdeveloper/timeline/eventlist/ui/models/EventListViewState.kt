package `in`.developingdeveloper.timeline.eventlist.ui.models

data class EventListViewState(
    val events: List<UIEventListItem>,
    val loading: Boolean,
    val alertMessage: String?,
    val errorMessage: String?,
    val isImportEventDialogShown: Boolean,
    val isImportingEvents: Boolean,
    val isExportingEvents: Boolean,
    val requestForEventExportDestination: Boolean,
    val exportStatusMessage: String?,
    val importEventTemplateFilePath: String?,
) {
    companion object {
        val Initial = EventListViewState(
            events = emptyList(),
            loading = false,
            alertMessage = null,
            errorMessage = null,
            isImportEventDialogShown = false,
            isImportingEvents = false,
            isExportingEvents = false,
            requestForEventExportDestination = false,
            exportStatusMessage = null,
            importEventTemplateFilePath = null,
        )
    }
}
