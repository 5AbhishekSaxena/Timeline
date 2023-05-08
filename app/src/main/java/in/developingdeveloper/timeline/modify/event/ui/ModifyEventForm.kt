package `in`.developingdeveloper.timeline.modify.event.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.developingdeveloper.timeline.R
import `in`.developingdeveloper.timeline.core.ui.components.FormInput
import `in`.developingdeveloper.timeline.core.ui.components.TimelineOutlinedTextField
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.modify.event.ui.components.OccurredOnInput
import `in`.developingdeveloper.timeline.modify.event.ui.components.TagsInput
import `in`.developingdeveloper.timeline.modify.event.ui.models.ModifyEventForm

@Composable
fun ModifyEventForm(
    form: ModifyEventForm,
    isFormEnabled: Boolean,
    onTitleValueChange: (String) -> Unit,
    onOccurredOnValueChange: (String) -> Unit,
    onModifyTagsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        TitleInput(
            title = form.title,
            onTitleValueChange = onTitleValueChange,
            titleErrorMessage = form.titleErrorMessage,
            enabled = isFormEnabled,
        )

        Spacer(modifier = Modifier.height(12.dp))

        OccurredOnInput(
            value = form.occurredOn,
            errorMessage = form.occurredOnErrorMessage,
            onValueChange = onOccurredOnValueChange,
            enabled = isFormEnabled,
        )

        Spacer(modifier = Modifier.height(12.dp))

        TagsInput(
            tags = form.tags,
            onModifyTagsClick = onModifyTagsClick,
        )
    }
}

@Composable
private fun TitleInput(
    title: String,
    onTitleValueChange: (String) -> Unit,
    titleErrorMessage: String?,
    enabled: Boolean,
) {
    FormInput(
        label = stringResource(id = R.string.title),
        input = {
            TitleInputField(title, onTitleValueChange, titleErrorMessage, enabled)
        },
    )
}

@Composable
private fun TitleInputField(
    title: String,
    onTitleValueChange: (String) -> Unit,
    titleErrorMessage: String?,
    enabled: Boolean,
) {
    TimelineOutlinedTextField(
        text = title,
        onTextChange = onTitleValueChange,
        enabled = enabled,
        errorMessage = titleErrorMessage,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
        ),
        singleLine = true,
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
private fun AddEventFormPreview() {
    val form = ModifyEventForm.Initial

    TimelineTheme {
        Surface {
            ModifyEventForm(
                form = form,
                isFormEnabled = true,
                onTitleValueChange = {},
                onOccurredOnValueChange = {},
                onModifyTagsClick = {},
            )
        }
    }
}
