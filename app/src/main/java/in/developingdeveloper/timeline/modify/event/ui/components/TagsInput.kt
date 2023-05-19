package `in`.developingdeveloper.timeline.modify.event.ui.components

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import `in`.developingdeveloper.timeline.R
import `in`.developingdeveloper.timeline.core.ui.components.FormInput
import `in`.developingdeveloper.timeline.core.ui.components.tag.TagLabel
import `in`.developingdeveloper.timeline.taglist.ui.models.UITag

@Composable
fun TagsInput(
    tags: Set<UITag>,
    onModifyTagsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FormInput(
        label = stringResource(R.string.tags),
        input = {
            TagsInputContent(
                tags = tags,
                onModifyTagsClick = onModifyTagsClick,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun TagsInputContent(
    tags: Set<UITag>,
    onModifyTagsClick: () -> Unit,
) {
    OutlinedCard(
        shape = RoundedCornerShape(4.0.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent),
        onClick = onModifyTagsClick,
    ) {
        val areTagsEmpty = remember(tags) { tags.isNotEmpty() }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp),
        ) {
            if (areTagsEmpty) {
                Tags(
                    tags = tags.map { it.label },
                    modifier = Modifier.weight(1f),
                )
            } else {
                NoTagsText(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 16.dp),
                )
            }

            TagsActionButton(
                areTagsEmpty = areTagsEmpty,
                onModifyTagsClick = onModifyTagsClick,
            )
        }
    }
}

@Composable
private fun Tags(
    tags: List<String>,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier
            .padding(12.dp),
    ) {
        tags.forEach {
            TagLabel(
                label = it,
                modifier = Modifier.padding(4.dp),
            )
        }
    }
}

@Composable
private fun NoTagsText(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "None yet",
        modifier = modifier,
    )
}

@Composable
private fun TagsActionButton(
    areTagsEmpty: Boolean,
    onModifyTagsClick: () -> Unit,
) {
    IconButton(
        onClick = onModifyTagsClick,
        modifier = Modifier
            .padding(vertical = 4.dp),
    ) {
        val icon = if (areTagsEmpty) {
            Icons.Default.Add
        } else {
            Icons.Default.Edit
        }

        Icon(imageVector = icon, contentDescription = null)
    }
}
