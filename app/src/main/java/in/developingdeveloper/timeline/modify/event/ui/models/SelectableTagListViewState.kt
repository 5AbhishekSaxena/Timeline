package `in`.developingdeveloper.timeline.modify.event.ui.models

import `in`.developingdeveloper.timeline.modify.tag.ui.models.ModifyTagViewState
import `in`.developingdeveloper.timeline.taglist.ui.models.UITag

data class SelectableTagListViewState(
    val tags: List<SelectableUITag>,
    val isLoading: Boolean,
    private val errorMessage: String?,
    val isNewTagAdding: Boolean,
    val addTagViewState: ModifyTagViewState,
) {

    val isRefreshing: Boolean
        get() = isLoading && tags.isNotEmpty()

    val message: String?
        get() = when {
            !errorMessage.isNullOrBlank() -> errorMessage
            tags.isEmpty() -> "No tags found."
            else -> null
        }

    val shouldDisplayTags: Boolean
        get() = tags.isNotEmpty()

    fun toLoading(): SelectableTagListViewState {
        return this.copy(isLoading = true)
    }

    fun toLoaded(
        selectedTags: Set<UITag>,
        selectableTags: List<SelectableUITag>,
    ): SelectableTagListViewState {
        val selectedTagIds = selectedTags.map { it.id }

        val updatedTags = selectableTags.map {
            it.copy(isSelected = it.id in selectedTagIds)
        }

        return Initial.copy(tags = updatedTags)
    }

    fun toError(errorMessage: String): SelectableTagListViewState {
        return Initial.copy(errorMessage = errorMessage)
    }

    fun resetFormAndIsNewTagAdding(): SelectableTagListViewState {
        return this.copy(
            addTagViewState = ModifyTagViewState.Initial,
            isNewTagAdding = false,
        )
    }

    companion object {
        val Initial = SelectableTagListViewState(
            tags = emptyList(),
            isLoading = false,
            errorMessage = null,
            isNewTagAdding = false,
            addTagViewState = ModifyTagViewState.Initial,
        )
    }
}
