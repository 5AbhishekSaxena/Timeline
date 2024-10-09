package `in`.developingdeveloper.timeline.eventlist.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.core.utils.export.excel.EventExporterResult
import `in`.developingdeveloper.timeline.eventlist.domain.usescases.EventExporterUseCase
import `in`.developingdeveloper.timeline.eventlist.domain.usescases.GetAllEventsUseCase
import `in`.developingdeveloper.timeline.eventlist.domain.usescases.SaveDestinationUriUseCase
import `in`.developingdeveloper.timeline.eventlist.ui.models.EventListViewState
import `in`.developingdeveloper.timeline.eventlist.ui.models.UIEventListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val eventExporterUseCase: EventExporterUseCase,
    private val saveDestinationUriUseCase: SaveDestinationUriUseCase,
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
        viewModelScope.launch {
            exportEventsInternal()
        }
    }

    fun exportEvents(uri: Uri) {
        viewModelScope.launch {
            saveDestinationUri(uri.toString())
            exportEventsInternal()
        }
    }

    private suspend fun saveDestinationUri(uri: String) {
        saveDestinationUriUseCase.invoke(uri)
    }

    private suspend fun exportEventsInternal() {
        _viewState.update { it.copy(isExportingEvents = true) }

        val events = viewState.value.events
            .filterIsInstance<UIEventListItem.Event>()
            .toEvents()

        eventExporterUseCase.invoke(events)
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .onCompletion {
                onEventExporterCompletion()
            }
            .catch {
                emit(EventExporterResult.Failure(it))
            }
            .collect {
                handleEventExporterResult(it)
            }
    }

    fun onEventExportDestinationRequested() {
        _viewState.update { it.copy(requestForEventExportDestination = false) }
    }

    fun onAlertMessageShown() {
        _viewState.update { it.copy(alertMessage = null) }
    }

    private fun handleEventExporterResult(result: EventExporterResult<String>) {
        when (result) {
            is EventExporterResult.StatusUpdate -> handleEventExporterStatusUpdateResult(result)
            is EventExporterResult.Success -> handleEventExporterSuccessResult()
            is EventExporterResult.Failure -> handleEventExporterFailureResult(result)
        }
    }

    private fun handleEventExporterStatusUpdateResult(result: EventExporterResult.StatusUpdate) {
        _viewState.update { it.copy(exportStatusMessage = result.status) }
    }

    private fun handleEventExporterSuccessResult() {
        _viewState.update {
            it.copy(
                exportStatusMessage = "Events exported successfully.",
                isExportingEvents = false,
            )
        }
    }

    private fun handleEventExporterFailureResult(result: EventExporterResult.Failure) {
        val message = result.error.message ?: "Something went wrong."

        val requestUserForEventExportDestination =
            message == "Destination folder changed or removed." ||
                message == "Destination folder not set."

        if (requestUserForEventExportDestination) {
            _viewState.update {
                it.copy(
                    requestForEventExportDestination = true,
                    isExportingEvents = false,
                    exportStatusMessage = null,
                )
            }
            return
        }

        _viewState.update {
            it.copy(
                alertMessage = message,
                exportStatusMessage = null,
                isExportingEvents = false,
            )
        }
    }

    private fun onEventExporterCompletion() {
        viewModelScope.launch {
            delay(4.seconds)
            _viewState.update {
                it.copy(
                    isExportingEvents = false,
                    exportStatusMessage = null,
                )
            }
        }
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

private fun List<UIEventListItem.Event>.toEvents(): List<Event> =
    this.map(UIEventListItem.Event::toEvent)

private fun UIEventListItem.Event.toEvent(): Event {
    return Event(
        id = this.id,
        title = this.title,
        tags = this.tags.toTags(),
        date = this.date,
        createdOn = this.createdOn,
    )
}

private fun List<String>.toTags(): List<Tag> {
    return this.map { Tag("", it) }
}
