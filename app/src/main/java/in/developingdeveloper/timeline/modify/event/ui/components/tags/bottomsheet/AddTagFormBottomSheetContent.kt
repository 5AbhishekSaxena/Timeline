package `in`.developingdeveloper.timeline.modify.event.ui.components.tags.bottomsheet

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.developingdeveloper.timeline.R
import `in`.developingdeveloper.timeline.core.ui.components.BackNavigationIcon
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.modify.tag.ui.ModifyTagForm
import `in`.developingdeveloper.timeline.modify.tag.ui.models.ModifyTagForm

@Composable
fun AddTagFormBottomSheetContent(
    form: ModifyTagForm,
    onLabelValueChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues? = null,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            BackNavigationIcon(onNavigationIconClick = onCancelClick)

            TitleTagListBottomSheet(
                text = stringResource(id = R.string.add_tag),
            )
        }

        ModifyTagForm(
            form = form,
            onLabelValueChange = onLabelValueChange,
            onAddClick = onAddClick,
            onCancelClick = onCancelClick,
            modifier = Modifier.then(
                if (contentPadding != null) {
                    Modifier.padding(contentPadding)
                } else {
                    Modifier
                },
            ),
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
private fun AAddTagFormBottomSheetContentPreview() {
    TimelineTheme {
        Surface {
            AddTagFormBottomSheetContent(
                form = ModifyTagForm.Initial,
                onLabelValueChange = {},
                onAddClick = {},
                onCancelClick = {},
                contentPadding = PaddingValues(16.dp),
            )
        }
    }
}
