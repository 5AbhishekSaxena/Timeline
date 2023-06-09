package `in`.developingdeveloper.timeline.modify.event.ui.components.tags.bottomsheet

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.modify.event.ui.models.SelectableTagListViewState
import `in`.developingdeveloper.timeline.modify.event.ui.models.SelectableUITag

@Composable
fun TagListBottomSheetContent(
    viewState: SelectableTagListViewState,
    onTagClick: (Int, SelectableUITag) -> Unit,
) {
    Card() {
    }

    TagListForBottomSheet(
        viewState = viewState,
        onTagClick = onTagClick,
        modifier = Modifier
            .fillMaxHeight(TagListBottomSheetDefaults.maxHeightPercentage)
            .wrapContentHeight(align = Alignment.Top),
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
private fun TagListBottomSheetPreview() {
    val tags = (1..10).map {
        SelectableUITag(
            id = it.toString(),
            label = "Tag $it",
            isSelected = false,
        )
    }

    val viewState = SelectableTagListViewState.Initial.copy(tags = tags)

    TimelineTheme {
        Surface {
            TagListBottomSheetContent(
                viewState = viewState,
                onTagClick = { _, _ -> },
            )
        }
    }
}
