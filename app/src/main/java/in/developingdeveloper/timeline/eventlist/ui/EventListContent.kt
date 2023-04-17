package `in`.developingdeveloper.timeline.eventlist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import `in`.developingdeveloper.timeline.R
import `in`.developingdeveloper.timeline.core.ui.components.TimelineCenterAlignedTopAppBar
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.eventlist.ui.components.EventList
import `in`.developingdeveloper.timeline.eventlist.ui.models.EventListViewState
import `in`.developingdeveloper.timeline.eventlist.ui.models.UIEvent
import java.time.LocalDateTime

@Composable
fun EventListContent(
    viewState: EventListViewState,
    onAddNewEventClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TimelineCenterAlignedTopAppBar(
                title = stringResource(id = R.string.app_name),
                actions = {
                    SettingsAction(onClick = onSettingsClick)
                },
            )
        },
        floatingActionButton = {
            AddEventFAB(onAddNewEventClick)
        },
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
                EventListContent(events = viewState.events)
            }
        }
    }
}

@Composable
private fun SettingsAction(onClick: () -> Unit) {
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
    events: List<UIEvent>,
    modifier: Modifier = Modifier,
) {
    EventList(
        events = events,
        modifier = modifier,
    )
}

@Composable
private fun AddEventFAB(onAddNewEventClick: () -> Unit) {
    FloatingActionButton(onClick = onAddNewEventClick) {
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
    val event = UIEvent(
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
                onAddNewEventClick = {},
                onSettingsClick = {},
            )
        }
    }
}
