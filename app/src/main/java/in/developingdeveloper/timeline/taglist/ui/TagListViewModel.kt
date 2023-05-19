package `in`.developingdeveloper.timeline.taglist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.taglist.domain.usecases.GetAllTagsUseCase
import `in`.developingdeveloper.timeline.taglist.ui.models.TagListViewState
import `in`.developingdeveloper.timeline.taglist.ui.models.UITag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TagListViewModel @Inject constructor(
    private val getAllTagsUseCase: GetAllTagsUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(TagListViewState.Initial)
    val viewState = _viewState.asStateFlow()

    init {
        getAllTags()
    }

    private fun getAllTags() {
        getAllTagsUseCase.invoke()
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .onStart {
                _viewState.update { it.toLoading() }
            }
            .onEach { result ->
                _viewState.update {
                    getViewStateForTagListResult(result)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getViewStateForTagListResult(result: Result<List<Tag>>): TagListViewState {
        val currentViewState = _viewState.value
        return result.fold(
            onSuccess = { tags ->
                val uiTags = tags.toUiTags()
                currentViewState.toLoaded(tags = uiTags)
            },
            onFailure = {
                val message = it.message ?: "Something went wrong."
                currentViewState.toError(errorMessage = message)
            },
        )
    }
}

private fun List<Tag>.toUiTags(): List<UITag> {
    return this.map(Tag::toUiTag)
}

private fun Tag.toUiTag(): UITag {
    return UITag(
        id = this.id,
        label = this.label,
    )
}
