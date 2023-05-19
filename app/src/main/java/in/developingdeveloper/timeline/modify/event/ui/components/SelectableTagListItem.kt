package `in`.developingdeveloper.timeline.modify.event.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.developingdeveloper.timeline.core.ui.components.tag.TagLabel
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.modify.event.ui.models.SelectableUITag

@Composable
fun SelectableTagListItem(
    tag: SelectableUITag,
    onTagClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clickable(onClick = onTagClick)
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TagLabel(label = tag.label)

            IsSelectedIcon(tag.isSelected)
        }

        Divider(
            color = Color.LightGray.copy(alpha = 0.4f),
            modifier = Modifier
                .padding(top = 8.dp),
        )
    }
}

@Composable
private fun IsSelectedIcon(isSelected: Boolean) {
    val icon = if (isSelected) {
        Icons.Default.CheckCircle
    } else {
        Icons.Outlined.Circle
    }

    val iconTint = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        LocalContentColor.current
    }

    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = iconTint,
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
private fun SelectableTagListItemPreview() {
    val tag = SelectableUITag(id = "", label = "Android", isSelected = false)

    TimelineTheme {
        Surface {
            Column {
                SelectableTagListItem(tag = tag, onTagClick = {})
                SelectableTagListItem(tag = tag.copy(isSelected = true), onTagClick = {})
            }
        }
    }
}
