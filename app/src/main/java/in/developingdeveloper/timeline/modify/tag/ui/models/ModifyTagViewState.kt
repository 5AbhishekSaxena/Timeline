package `in`.developingdeveloper.timeline.modify.tag.ui.models

data class ModifyTagViewState(
    val form: ModifyTagForm,
    val isNewTag: Boolean,
    val isLoading: Boolean,
    val isFormEnabled: Boolean,
    val isCompleted: Boolean,
    val isDeleted: Boolean,
    val errorMessage: String?,
) {
    companion object {
        val Initial = ModifyTagViewState(
            form = ModifyTagForm.Initial,
            isNewTag = false,
            isLoading = false,
            isFormEnabled = true,
            isCompleted = false,
            isDeleted = false,
            errorMessage = null,
        )
    }
}
