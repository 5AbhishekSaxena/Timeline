package `in`.developingdeveloper.timeline.modify.event.ui.models

data class ModifyEventViewState(
    val form: ModifyEventForm,
    val tagListViewState: SelectableTagListViewState,
    val modifyTags: Boolean,
    val formEnabled: Boolean,
    val isLoading: Boolean,
    val isCompleted: Boolean,
    val errorMessage: String?,
    val isNewEvent: Boolean,
) {
    companion object {
        val Initial = ModifyEventViewState(
            form = ModifyEventForm.Initial,
            tagListViewState = SelectableTagListViewState.Initial,
            modifyTags = false,
            formEnabled = true,
            isLoading = false,
            isCompleted = false,
            errorMessage = null,
            isNewEvent = true,
        )
    }
}
