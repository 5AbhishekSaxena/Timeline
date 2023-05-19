package `in`.developingdeveloper.timeline.taglist.ui.models

data class TagListViewState(
    val tags: List<UITag>,
    val isLoading: Boolean,
    private val errorMessage: String?,
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

    fun toLoading(): TagListViewState {
        return this.copy(isLoading = true)
    }

    fun toLoaded(tags: List<UITag>): TagListViewState {
        return Initial.copy(tags = tags)
    }

    fun toError(errorMessage: String): TagListViewState {
        return Initial.copy(errorMessage = errorMessage)
    }

    companion object {
        val Initial = TagListViewState(
            tags = emptyList(),
            isLoading = false,
            errorMessage = null,
        )
    }
}
