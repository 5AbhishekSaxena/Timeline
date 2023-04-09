package `in`.developingdeveloper.timeline.eventlist.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.eventlist.ui.models.UIEvent
import java.time.LocalDateTime

@Composable
fun EventList(
    events: List<UIEvent>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        items(events) { event ->
            EventListItem(event = event)
        }
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
private fun EventListPreview() {
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

    TimelineTheme {
        Surface {
            EventList(events)
        }
    }
}
