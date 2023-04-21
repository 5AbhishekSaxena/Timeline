package `in`.developingdeveloper.timeline.taglist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.developingdeveloper.timeline.R
import `in`.developingdeveloper.timeline.core.ui.components.BackNavigationIcon
import `in`.developingdeveloper.timeline.core.ui.components.TimelineStartAlignedTopAppBar
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.taglist.ui.components.TagList
import `in`.developingdeveloper.timeline.taglist.ui.models.TagListViewState
import `in`.developingdeveloper.timeline.taglist.ui.models.UITag

@Composable
fun TagListContent(
    snackbarHostState: SnackbarHostState,
    viewState: TagListViewState,
    onNavigationIconClick: () -> Unit,
    onAddTagClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            AddTagTopBar(onNavigationIconClick)
        },
        floatingActionButton = {
            AddTagFloatingActionButton(onAddTagClick)
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
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
                TagList(tags = viewState.tags)
            }
        }
    }
}

@Composable
private fun AddTagTopBar(onNavigationIconClick: () -> Unit) {
    TimelineStartAlignedTopAppBar(
        title = stringResource(id = R.string.tags),
        navigationIcon = {
            BackNavigationIcon(onNavigationIconClick)
        },
    )
}

@Composable
private fun AddTagFloatingActionButton(onAddTagClick: () -> Unit) {
    FloatingActionButton(onClick = onAddTagClick) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
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
private fun TagListContentPreview() {
    val tags = (1..20).map { UITag("Random Tag #$it") }

    val viewState = TagListViewState.Initial.copy(tags = tags)

    TimelineTheme {
        Surface {
            TagListContent(
                snackbarHostState = SnackbarHostState(),
                viewState = viewState,
                onNavigationIconClick = {},
                onAddTagClick = {},
            )
        }
    }
}
