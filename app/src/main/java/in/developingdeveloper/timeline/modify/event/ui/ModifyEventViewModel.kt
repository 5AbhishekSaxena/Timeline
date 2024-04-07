package `in`.developingdeveloper.timeline.modify.event.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.core.utils.generateRandomUUID
import `in`.developingdeveloper.timeline.modify.event.domain.exceptions.ModifyEventException
import `in`.developingdeveloper.timeline.modify.event.domain.usecases.DeleteEventUseCase
import `in`.developingdeveloper.timeline.modify.event.domain.usecases.GetEventByIdUseCase
import `in`.developingdeveloper.timeline.modify.event.domain.usecases.ModifyEventUseCase
import `in`.developingdeveloper.timeline.modify.event.ui.models.ModifyEventForm
import `in`.developingdeveloper.timeline.modify.event.ui.models.ModifyEventViewState
import `in`.developingdeveloper.timeline.modify.event.ui.models.SelectableTagListViewState
import `in`.developingdeveloper.timeline.modify.event.ui.models.SelectableUITag
import `in`.developingdeveloper.timeline.modify.tag.domain.exceptions.ModifyTagException
import `in`.developingdeveloper.timeline.modify.tag.domain.usecases.ModifyTagUseCase
import `in`.developingdeveloper.timeline.modify.tag.ui.models.ModifyTagViewState
import `in`.developingdeveloper.timeline.taglist.domain.usecases.GetAllTagsUseCase
import `in`.developingdeveloper.timeline.taglist.ui.models.UITag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ModifyEventViewModel @Inject constructor(
    private val modifyEventUseCase: ModifyEventUseCase,
    private val modifyTagUseCase: ModifyTagUseCase,
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val getAllTagsUseCase: GetAllTagsUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyEventViewState.Initial)
    val viewState = _viewState.asStateFlow()

    private var areTagsLoaded = false

    private var eventId: String? = null

    fun init(eventId: String?) {
        setEventId(eventId)
        setIsNewEvent(eventId == null)
        getEventDetailsForExistingEvent()
    }

    private fun getEventDetailsForExistingEvent() {
        if (_viewState.value.isNewEvent) return

        val eventId = eventId ?: return

        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }
            val result = getEventByIdUseCase.invoke(eventId)

            _viewState.value = getViewStateForGetEventById(result)

            _viewState.update { it.copy(isLoading = false) }
        }
    }

    private fun getViewStateForGetEventById(result: Result<Event>): ModifyEventViewState {
        val currentViewState = _viewState.value

        return result.fold(
            onSuccess = { event ->
                val updatedForm = currentViewState.form.copy(
                    title = event.title,
                    tags = event.tags.toUITags().toSet(),
                    occurredOn = event.date,
                )

                currentViewState.copy(form = updatedForm)
            },
            onFailure = {
                currentViewState.copy(errorMessage = it.message ?: "Failed to load event.")
            },
        )
    }

    private fun setEventId(eventId: String?) {
        this.eventId = eventId ?: generateRandomUUID()
    }

    private fun setIsNewEvent(isNewEvent: Boolean) {
        _viewState.update { it.copy(isNewEvent = isNewEvent) }
    }

    fun onTitleValueChange(title: String) {
        _viewState.update {
            val updatedForm = it.form.copy(
                title = title,
                titleErrorMessage = null,
            )
            it.copy(form = updatedForm)
        }
    }

    fun onOccurredValueChange(occurredOn: String) {
        _viewState.update {
            val updatedForm = it.form.copy(
                occurredOn = LocalDateTime.parse(occurredOn),
                occurredOnErrorMessage = null,
            )
            it.copy(form = updatedForm)
        }
    }

    fun onModifyTagsClick() {
        lazilyFetchTags()

        _viewState.update { it.copy(modifyTags = true) }
    }

    private fun lazilyFetchTags() {
        if (areTagsLoaded) return

        areTagsLoaded = true
        getAllTags()
    }

    private fun getAllTags() {
        getAllTagsUseCase.invoke()
            .distinctUntilChanged()
            .onStart {
                _viewState.update {
                    val updatedTagListViewState = it.tagListViewState.toLoading()
                    it.copy(tagListViewState = updatedTagListViewState)
                }
            }
            .onEach { result ->
                _viewState.update {
                    getAddEventViewStateForResult(result)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getAddEventViewStateForResult(result: Result<List<Tag>>): ModifyEventViewState {
        val currentAddEventViewState = _viewState.value

        val updatedTagListViewState =
            getTagListViewStateForResult(
                result = result,
                selectedTags = currentAddEventViewState.form.tags,
                currentTagListViewState = currentAddEventViewState.tagListViewState,
            )

        return currentAddEventViewState.copy(
            tagListViewState = updatedTagListViewState,
        )
    }

    private fun getTagListViewStateForResult(
        result: Result<List<Tag>>,
        selectedTags: Set<UITag>,
        currentTagListViewState: SelectableTagListViewState,
    ): SelectableTagListViewState {
        return result.fold(
            onSuccess = { tags ->
                val uiTags = tags.toSelectableUiTags()
                currentTagListViewState.toLoaded(
                    selectedTags = selectedTags,
                    selectableTags = uiTags,
                )
            },
            onFailure = {
                val message = it.message ?: "Something went wrong."
                currentTagListViewState.toError(errorMessage = message)
            },
        )
    }

    fun onDoneClick() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true, formEnabled = false) }

            val eventId = eventId ?: generateRandomUUID()
            val eventToCreate = _viewState.value.form.toEvent(eventId)

            val result = modifyEventUseCase.invoke(eventToCreate, _viewState.value.isNewEvent)
            _viewState.value = getViewStateForModifyEventResult(result)

            _viewState.update { it.copy(isLoading = false, formEnabled = true) }
        }
    }

    private fun getViewStateForModifyEventResult(result: Result<Unit>): ModifyEventViewState {
        val currentViewState = _viewState.value

        return result.fold(
            onSuccess = {
                currentViewState.copy(
                    isCompleted = true,
                )
            },
            onFailure = { throwable ->
                if (throwable is ModifyEventException) {
                    val updatedForm = getUpdatedFormForFormExceptions(throwable, currentViewState)
                    currentViewState.copy(
                        isCompleted = false,
                        form = updatedForm,
                    )
                } else {
                    val defaultErrorMessage =
                        if (_viewState.value.isNewEvent) "Error storing event." else "Error updating event"

                    val errorMessage = throwable.message ?: defaultErrorMessage
                    currentViewState.copy(
                        isCompleted = false,
                        errorMessage = errorMessage,
                    )
                }
            },
        )
    }

    private fun getUpdatedFormForFormExceptions(
        exception: ModifyEventException,
        currentViewState: ModifyEventViewState,
    ): ModifyEventForm {
        return when (exception) {
            is ModifyEventException.InvalidTitleException ->
                currentViewState.form.copy(
                    titleErrorMessage = exception.message,
                )

            is ModifyEventException.InvalidOccurredOnException ->
                currentViewState.form.copy(
                    occurredOnErrorMessage = exception.message,
                )
        }
    }

    fun onModifyTagsCompleted() {
        _viewState.update { it.copy(modifyTags = false) }
    }

    fun onTagClick(index: Int, tag: SelectableUITag) {
        _viewState.update {
            val uiTag = tag.toUITag()
            val isAlreadySelected = it.form.tags.contains(uiTag)
            val updatedTags = if (isAlreadySelected) {
                it.form.tags - uiTag
            } else {
                it.form.tags + uiTag
            }

            val updatedSelectableTag = tag.copy(isSelected = !isAlreadySelected)
            val selectableTags = it.tagListViewState.tags
            val updatedSelectableTags = selectableTags.toMutableList()
            updatedSelectableTags[index] = updatedSelectableTag

            val updatedForm = it.form.copy(tags = updatedTags)
            it.copy(
                form = updatedForm,
                tagListViewState = it.tagListViewState.copy(tags = updatedSelectableTags),
            )
        }
    }

    fun onAddTagBottomSheetClick() {
        _viewState.update {
            val currentListViewState = it.tagListViewState
            val updatedTagListViewState = currentListViewState.copy(isNewTagAdding = true)
            it.copy(tagListViewState = updatedTagListViewState)
        }
    }

    fun onAddTagBottomSheetLabelValueChange(label: String) {
        _viewState.update {
            val currentListViewState = it.tagListViewState
            val updatedAddTagForm =
                currentListViewState.addTagViewState.form.copy(label = label)
            val updatedAddTagViewState =
                currentListViewState.addTagViewState.copy(form = updatedAddTagForm)
            val updatedTagListViewState =
                currentListViewState.copy(addTagViewState = updatedAddTagViewState)
            it.copy(tagListViewState = updatedTagListViewState)
        }
    }

    fun onAddTagFormBottomSheetClick() {
        _viewState.update {
            val currentAddTagViewState = it.tagListViewState.addTagViewState
            val updatedAddTagViewState = currentAddTagViewState.copy(
                isLoading = true,
                isFormEnabled = false,
            )

            val updatedTagListViewState =
                it.tagListViewState.copy(addTagViewState = updatedAddTagViewState)

            it.copy(tagListViewState = updatedTagListViewState)
        }

        val tagId = generateRandomUUID()
        val label = _viewState.value.tagListViewState.addTagViewState.form.label

        val tagToCreate = Tag(
            id = tagId,
            label = label,
        )

        viewModelScope.launch {
            val result = modifyTagUseCase.invoke(tagToCreate, true)
            val updatedAddFormViewState = getViewStateForModifyTagResult(result)

            _viewState.update {
                val updatedTagListViewState =
                    it.tagListViewState.copy(addTagViewState = updatedAddFormViewState)

                it.copy(tagListViewState = updatedTagListViewState)
            }
        }

        _viewState.update {
            val currentAddTagViewState = it.tagListViewState.addTagViewState
            val updatedAddTagViewState = currentAddTagViewState.copy(
                isLoading = false,
                isFormEnabled = true,
            )

            val updatedTagListViewState =
                it.tagListViewState.copy(addTagViewState = updatedAddTagViewState)

            it.copy(tagListViewState = updatedTagListViewState)
        }
    }

    private fun getViewStateForModifyTagResult(result: Result<Unit>): ModifyTagViewState {
        val currentAddTagViewState = _viewState.value.tagListViewState.addTagViewState

        return result.fold(
            onSuccess = {
                currentAddTagViewState.copy(
                    isLoading = false,
                    isFormEnabled = false,
                    isCompleted = true,
                )
            },
            onFailure = { throwable ->

                when (throwable) {
                    is ModifyTagException.InvalidLabelException -> {
                        val updatedForm =
                            currentAddTagViewState.form.copy(labelErrorMessage = throwable.message)
                        currentAddTagViewState.copy(form = updatedForm)
                    }

                    else -> {
                        val message = throwable.message ?: "Something went wrong."

                        currentAddTagViewState.copy(
                            isLoading = false,
                            isFormEnabled = true,
                            errorMessage = message,
                        )
                    }
                }
            },
        )
    }

    fun onCancelFormBottomSheetClick() {
        _viewState.update {
            val updatedAddTagViewState = it
                .tagListViewState
                .resetFormAndIsNewTagAdding()

            it.copy(tagListViewState = updatedAddTagViewState)
        }
    }

    fun onDeleteClick() {
        val eventId = this.eventId ?: return
        viewModelScope.launch {
            deleteEventUseCase(eventId)
                .fold(
                    onSuccess = {
                        _viewState.update { it.copy(isDeleted = true) }
                    },
                    onFailure = { error ->
                        val errorMessage = error.message ?: "Failed to delete event."

                        _viewState.update {
                            it.copy(errorMessage = errorMessage)
                        }
                    },
                )
        }
    }
}

private fun ModifyEventForm.toEvent(eventId: String): Event {
    return Event(
        id = eventId,
        title = this.title,
        tags = this.tags.toTags(),
        date = this.occurredOn,
        createdOn = LocalDateTime.now(),
    )
}

private fun List<Tag>.toUITags(): List<UITag> {
    return this.map(Tag::toUITag)
}

private fun Tag.toUITag(): UITag {
    return UITag(
        id = this.id,
        label = this.label,
    )
}

private fun Set<UITag>.toTags(): List<Tag> {
    return this.map(UITag::toTag)
}

private fun UITag.toTag(): Tag {
    return Tag(
        id = this.id,
        label = this.label,
    )
}

private fun SelectableUITag.toUITag(): UITag {
    return UITag(
        id = this.id,
        label = this.label,
    )
}

private fun List<Tag>.toSelectableUiTags(): List<SelectableUITag> {
    return this.map(Tag::toSelectableUiTag)
}

private fun Tag.toSelectableUiTag(): SelectableUITag {
    return SelectableUITag(
        id = this.id,
        label = this.label,
        isSelected = false,
    )
}
