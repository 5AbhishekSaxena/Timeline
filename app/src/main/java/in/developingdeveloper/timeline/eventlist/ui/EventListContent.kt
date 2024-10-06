package `in`.developingdeveloper.timeline.eventlist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import `in`.developingdeveloper.timeline.R
import `in`.developingdeveloper.timeline.core.ui.components.TimelineCenterAlignedTopAppBar
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.eventlist.ui.components.EventList
import `in`.developingdeveloper.timeline.eventlist.ui.models.EventListViewState
import `in`.developingdeveloper.timeline.eventlist.ui.models.UIEventListItem
import java.time.LocalDateTime

@Composable
fun EventListContent(
    viewState: EventListViewState,
    onAlertMessageShown: () -> Unit,
    onExportEventClick: () -> Unit,
    onEventListItemClick: (UIEventListItem) -> Unit,
    onAddEventClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewState.alertMessage) {
        viewState.alertMessage?.let {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(message = it)
            onAlertMessageShown()
        }
    }

    Scaffold(
        topBar = {
            TimelineCenterAlignedTopAppBar(
                title = stringResource(id = R.string.app_name),
                actions = {
                    ExportEventsAction(
                        enabled = !viewState.isExportingEvents,
                        onClick = onExportEventClick,
                    )
                    SettingsAction(onClick = onSettingsClick)
                },
            )
        },
        floatingActionButton = {
            AddEventFAB(onAddEventClick = onAddEventClick)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            if (viewState.loading) {
                LoadingContent()
            }

            if (viewState.events.isEmpty()) {
                EmptyListContent()
            } else {
                EventListContent(
                    events = viewState.events,
                    onEventListItemClick = onEventListItemClick,
                )
            }
        }
    }
}

@Composable
private fun ExportEventsAction(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        enabled = enabled,
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.IosShare,
            contentDescription = "Share events",
        )
    }
}

@Composable
private fun SettingsAction(
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(id = R.string.settings),
        )
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(),
    )
}

@Composable
private fun EmptyListContent(modifier: Modifier = Modifier) {
    Text(
        text = "No events found!",
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(),
    )
}

@Composable
private fun EventListContent(
    events: List<UIEventListItem>,
    onEventListItemClick: (UIEventListItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    EventList(
        events = events,
        onEventListItemClick = onEventListItemClick,
        modifier = modifier,
    )
}

@Composable
private fun AddEventFAB(onAddEventClick: () -> Unit) {
    FloatingActionButton(onClick = onAddEventClick) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.add_event),
        )
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("UnusedPrivateMember", "MagicNumber")
private fun EventListContentPreview() {
    val event = UIEventListItem.Event(
        "",
        "Sample title",
        listOf("#Android", "#Kotlin"),
        LocalDateTime.now(),
        LocalDateTime.now(),
    )

    val events = (1..10).map {
        event.copy(id = it.toString())
    }

    val viewState = EventListViewState.Initial.copy(events = events)

    TimelineTheme {
        Surface {
            EventListContent(
                viewState = viewState,
                onAlertMessageShown = {},
                onExportEventClick = {},
                onEventListItemClick = {},
                onAddEventClick = {},
                onSettingsClick = {},
            )
        }
    }
}
