package `in`.developingdeveloper.timeline.taglist.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.taglist.ui.models.TagListViewState
import `in`.developingdeveloper.timeline.taglist.ui.models.UITag

@Composable
fun TagList(
    tags: List<UITag>,
    modifier: Modifier = Modifier,
    onTagClick: (UITag) -> Unit = {},
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier
            .fillMaxSize(),
    ) {
        items(tags) { tag ->
            TagListItem(
                tag = tag,
                onTagClick = onTagClick,
            )
        }
    }
}

@Composable
fun TagList(
    viewState: TagListViewState,
    onTagListItemClick: (UITag) -> Unit,
) {
    when {
        viewState.isRefreshing -> {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        viewState.isLoading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
            )
        }

        else -> {
            Box(modifier = Modifier.height(4.dp))
        }
    }

    viewState.message?.let { message ->
        CenterText(message)
    }

    if (viewState.shouldDisplayTags) {
        TagList(tags = viewState.tags, onTagClick = onTagListItemClick)
    }
}

@Composable
private fun CenterText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
    )
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
private fun TagListPreview() {
    val tags = (1..10).map {
        UITag(
            id = it.toString(),
            label = "Tag $it",
        )
    }

    TimelineTheme {
        Surface {
            TagList(tags = tags)
        }
    }
}
