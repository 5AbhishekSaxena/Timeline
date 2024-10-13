package `in`.developingdeveloper.timeline.eventlist.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import `in`.developingdeveloper.timeline.R
import `in`.developingdeveloper.timeline.core.ui.components.TimelineCenterAlignedTopAppBar
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.eventlist.ui.components.EventList
import `in`.developingdeveloper.timeline.eventlist.ui.components.ProgressUpdateSnackbar
import `in`.developingdeveloper.timeline.eventlist.ui.models.EventListViewState
import `in`.developingdeveloper.timeline.eventlist.ui.models.UIEventListItem
import java.time.LocalDateTime

@Composable
@Suppress("LongMethod")
fun EventListContent(
    viewState: EventListViewState,
    onAlertMessageShown: () -> Unit,
    onImportEventClick: () -> Unit,
    onImportDialogDismiss: () -> Unit,
    onImportDialogGenerateTemplateClick: () -> Unit,
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

    if (viewState.isImportEventDialogShown) {
        ImportEventsDialog(
            onDismiss = onImportDialogDismiss,
            onGenerateTemplateClick = onImportDialogGenerateTemplateClick,
        )
    }

    Scaffold(
        topBar = {
            TimelineCenterAlignedTopAppBar(
                title = stringResource(id = R.string.app_name),
                actions = {
                    ImportEventsAction(
                        enabled = !viewState.isImportingEvents,
                        onClick = onImportEventClick,
                    )
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
        Box {
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

            ProgressUpdateSnackbar(
                isVisible = viewState.exportStatusMessage != null,
                message = viewState.exportStatusMessage ?: "",
                onDismiss = {},
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun ImportEventsDialog(
    onDismiss: () -> Unit,
    onGenerateTemplateClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = "Import Events",
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Placeholder for file picker for the excel.")
                }

                Spacer(Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextButton(
                        onClick = onGenerateTemplateClick,
                    ) {
                        Text("Generate Template")
                    }
                }
            }
        }
    }
}

@Composable
private fun ImportEventsAction(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        enabled = enabled,
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Import events",
        )
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
                onImportEventClick = {},
                onImportDialogDismiss = {},
                onImportDialogGenerateTemplateClick = {},
                onExportEventClick = {},
                onEventListItemClick = {},
                onAddEventClick = {},
                onSettingsClick = {},
            )
        }
    }
}
