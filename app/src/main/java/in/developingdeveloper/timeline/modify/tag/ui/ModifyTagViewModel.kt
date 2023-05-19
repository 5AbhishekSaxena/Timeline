package `in`.developingdeveloper.timeline.modify.tag.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.core.utils.generateRandomUUID
import `in`.developingdeveloper.timeline.modify.tag.domain.exceptions.ModifyTagException
import `in`.developingdeveloper.timeline.modify.tag.domain.usecases.GetTagByIdUseCase
import `in`.developingdeveloper.timeline.modify.tag.domain.usecases.ModifyTagUseCase
import `in`.developingdeveloper.timeline.modify.tag.ui.models.ModifyTagViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyTagViewModel @Inject constructor(
    private val getTagByIdUseCase: GetTagByIdUseCase,
    private val modifyTagUseCase: ModifyTagUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyTagViewState.Initial)
    val viewState = _viewState.asStateFlow()

    private var isNewTag: Boolean = false

    private var tagId: String? = null

    fun init(tagId: String?) {
        setTagId(tagId)
        setIsNewTag(tagId)
        getTagDetailsForExistingTag()
    }

    private fun setTagId(tagId: String?) {
        this.tagId = tagId
    }

    private fun setIsNewTag(tagId: String?) {
        isNewTag = tagId == null
    }

    private fun getTagDetailsForExistingTag() {
        if (isNewTag) return

        val tagId = tagId ?: return

        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }

            val result = getTagByIdUseCase.invoke(tagId)
            _viewState.value = getViewStateForGetTagById(result)
        }
    }

    private fun getViewStateForGetTagById(result: Result<Tag>): ModifyTagViewState {
        val currentViewState = _viewState.value

        return result.fold(
            onSuccess = { tag ->
                val updatedForm = currentViewState.form.copy(label = tag.label)
                currentViewState.copy(form = updatedForm)
            },
            onFailure = {
                currentViewState.copy(errorMessage = it.message ?: "Failed to load tag.")
            },
        )
    }

    private fun getViewStateForModifyTagResult(result: Result<Unit>): ModifyTagViewState {
        val currentViewState = _viewState.value

        return result.fold(
            onSuccess = {
                currentViewState.copy(
                    isLoading = false,
                    isFormEnabled = false,
                    isCompleted = true,
                )
            },
            onFailure = { throwable ->

                when (throwable) {
                    is ModifyTagException.InvalidLabelException -> {
                        val updatedForm =
                            currentViewState.form.copy(labelErrorMessage = throwable.message)
                        currentViewState.copy(form = updatedForm)
                    }

                    else -> {
                        val message = throwable.message ?: "Something went wrong."

                        currentViewState.copy(
                            isLoading = false,
                            isFormEnabled = true,
                            errorMessage = message,
                        )
                    }
                }
            },
        )
    }

    fun onLabelValueChange(label: String) {
        _viewState.update {
            val updatedForm = it.form.copy(label = label)
            it.copy(form = updatedForm)
        }
    }

    fun onAddClick() {
        _viewState.update { it.copy(isLoading = true, isFormEnabled = false) }

        val tagId = tagId ?: generateRandomUUID()
        val label = _viewState.value.form.label

        val tagToCreate = Tag(
            id = tagId,
            label = label,
        )

        viewModelScope.launch {
            val result = modifyTagUseCase.invoke(tagToCreate, isNewTag)
            _viewState.value = getViewStateForModifyTagResult(result)
        }
    }
}
