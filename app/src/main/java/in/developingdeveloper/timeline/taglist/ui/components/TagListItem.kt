package `in`.developingdeveloper.timeline.taglist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import `in`.developingdeveloper.timeline.core.ui.components.tag.TagLabel
import `in`.developingdeveloper.timeline.taglist.ui.models.UITag

@Composable
fun TagListItem(
    tag: UITag,
    onTagClick: (UITag) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clickable(onClick = { onTagClick(tag) })
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        TagLabel(
            label = tag.label,
            modifier = Modifier.padding(4.dp),
        )

        Divider(
            color = Color.LightGray.copy(alpha = 0.4f),
            modifier = Modifier
                .padding(top = 8.dp),
        )
    }
}
