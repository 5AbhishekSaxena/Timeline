package `in`.developingdeveloper.timeline.eventlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.eventlist.domain.datasource.EventExporterUseCase
import `in`.developingdeveloper.timeline.eventlist.domain.usescases.GetAllEventsUseCase
import `in`.developingdeveloper.timeline.eventlist.ui.models.EventListViewState
import `in`.developingdeveloper.timeline.eventlist.ui.models.UIEventListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val eventExporterUseCase: EventExporterUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(EventListViewState.Initial)
    val viewState = _viewState.asStateFlow()

    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")

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
                val uiEventListItems = uiEvents
                    .groupBy { YearMonth.from(it.date) }
                    .flatMap { (yearMonth, event) ->
                        val header: String = dateTimeFormatter.format(yearMonth)
                        listOf(UIEventListItem.Header(header)) + event
                    }
                currentViewState.copy(events = uiEventListItems, loading = false)
            },
            onFailure = {
                val message = it.message ?: "Something went wrong."
                currentViewState.copy(loading = false, errorMessage = message)
            },
        )
    }

    fun exportEvents() {
        _viewState.update { it.copy(isExportingEvents = true) }
        val result = eventExporterUseCase.invoke()
        result.fold(
            onSuccess = {
                _viewState.update {
                    it.copy(
                        alertMessage = "Events exported successfully.",
                        isExportingEvents = false,
                    )
                }
            },
            onFailure = { error ->
                val message = error.message ?: "Something went wrong."
                val requestUserForEventExportDestination =
                    message == "Destination folder uri is null"

                if (requestUserForEventExportDestination) {
                    _viewState.update {
                        it.copy(
                            requestForEventExportDestination = true,
                            isExportingEvents = false,
                        )
                    }
                    return
                }

                _viewState.update {
                    it.copy(
                        alertMessage = message,
                        isExportingEvents = false,
                    )
                }
            },
        )
    }

    fun onEventExportDestinationRequested() {
        _viewState.update { it.copy(requestForEventExportDestination = false) }
    }

    fun onAlertMessageShown() {
        _viewState.update { it.copy(alertMessage = null) }
    }
}

private fun List<Event>.toUiEvents(): List<UIEventListItem.Event> = this.map(Event::toUiEvent)

private fun Event.toUiEvent(): UIEventListItem.Event {
    return UIEventListItem.Event(
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
