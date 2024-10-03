package `in`.developingdeveloper.timeline.eventlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.eventlist.domain.usescases.GetAllEventsUseCase
import `in`.developingdeveloper.timeline.eventlist.ui.models.EventListItem
import `in`.developingdeveloper.timeline.eventlist.ui.models.EventListViewState
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
class EventListViewModel @Inject constructor(
    private val getAllEventsUseCase: GetAllEventsUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(EventListViewState.Initial)
    val viewState = _viewState.asStateFlow()

    init {
        getAllEvents()
    }

    private fun getAllEvents() {
        getAllEventsUseCase.invoke()
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .onStart {
                _viewState.update { it.copy(loading = true) }
            }
            .onEach { result ->
                _viewState.update { getViewStateForEventListResult(result) }
            }
            .launchIn(viewModelScope)
    }

    private fun getViewStateForEventListResult(result: Result<List<Event>>): EventListViewState {
        val currentViewState = _viewState.value
        return result.fold(
            onSuccess = { events ->
                val uiEvents = events.toUiEvents()
                currentViewState.copy(events = uiEvents, loading = false)
            },
            onFailure = {
                val message = it.message ?: "Something went wrong."
                currentViewState.copy(loading = false, errorMessage = message)
            },
        )
    }
}

private fun List<Event>.toUiEvents(): List<EventListItem.UIEvent> = this.map(Event::toUiEvent)

private fun Event.toUiEvent(): EventListItem.UIEvent {
    return EventListItem.UIEvent(
        id = this.id,
        title = this.title,
        tags = this.tags.toUITags(),
        date = this.date,
        createdOn = this.createdOn,
    )
}

private fun List<Tag>.toUITags(): List<String> {
    return this.map { it.label }
}
