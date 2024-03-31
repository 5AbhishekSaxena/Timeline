package `in`.developingdeveloper.timeline.modify.event.ui.components.tags.bottomsheet

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.modify.event.ui.models.SelectableTagListViewState
import `in`.developingdeveloper.timeline.modify.event.ui.models.SelectableUITag

@Composable
fun TagListBottomSheetContent(
    viewState: SelectableTagListViewState,
    onTagClick: (Int, SelectableUITag) -> Unit,
    onAddTagClick: () -> Unit,
    onLabelValueChange: (String) -> Unit,
    onAddTagFormClick: () -> Unit,
    onCancelFormClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(TagListBottomSheetDefaults.maxHeightPercentage)
            .wrapContentHeight(align = Alignment.Top),
    ) {
        if (viewState.isNewTagAdding) {
            AddTagFormBottomSheetContent(
                form = viewState.addTagViewState.form,
                onLabelValueChange = onLabelValueChange,
                onAddClick = onAddTagFormClick,
                onCancelClick = onCancelFormClick,
                contentPadding = PaddingValues(16.dp),
            )
        } else {
            TagListForBottomSheet(
                viewState = viewState,
                onTagClick = onTagClick,
                onAddTagClick = onAddTagClick,
            )
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
                onAddTagClick = {},
                onLabelValueChange = {},
                onAddTagFormClick = {},
                onCancelFormClick = {},
            )
        }
    }
}
