package `in`.developingdeveloper.timeline.modify.tag.ui.models

data class ModifyTagViewState(
    val form: ModifyTagForm,
    val isLoading: Boolean,
    val isFormEnabled: Boolean,
    val isCompleted: Boolean,
    val errorMessage: String?,
) {
    companion object {
        val Initial = ModifyTagViewState(
            form = ModifyTagForm.Initial,
            isLoading = false,
            isFormEnabled = true,
            isCompleted = false,
            errorMessage = null,
        )
    }
}
