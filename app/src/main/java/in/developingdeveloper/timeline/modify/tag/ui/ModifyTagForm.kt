package `in`.developingdeveloper.timeline.modify.tag.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.developingdeveloper.timeline.R
import `in`.developingdeveloper.timeline.core.ui.components.FormInput
import `in`.developingdeveloper.timeline.core.ui.components.TimelineOutlinedTextField
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.modify.tag.ui.models.ModifyTagForm
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val KEYBOARD_SHOW_DELAY = 200L

@Composable
fun ModifyTagForm(
    form: ModifyTagForm,
    onLabelValueChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val labelFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        labelFocusRequester.requestFocus()
    }

    LaunchedEffect(key1 = form.labelErrorMessage) {
        if (form.labelErrorMessage == null) return@LaunchedEffect
        labelFocusRequester.requestFocus()
    }

    Column(modifier = modifier) {
        LabelInput(
            label = form.label,
            onLabelValueChange = onLabelValueChange,
            labelErrorMessage = form.labelErrorMessage,
            modifier = Modifier
                .focusRequester(labelFocusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            delay(KEYBOARD_SHOW_DELAY)
                            keyboardController?.show()
                        }
                    } else {
                        keyboardController?.hide()
                    }
                },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Actions(
            onAddClick = onAddClick,
            onCancelClick = onCancelClick,
        )
    }
}

@Composable
private fun LabelInput(
    label: String,
    onLabelValueChange: (String) -> Unit,
    labelErrorMessage: String?,
    modifier: Modifier = Modifier,
) {
    FormInput(
        label = stringResource(id = R.string.label),
        input = {
            LabelInputField(
                label = label,
                onLabelValueChange = onLabelValueChange,
                errorMessage = labelErrorMessage,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun LabelInputField(
    label: String,
    onLabelValueChange: (String) -> Unit,
    errorMessage: String?,
    modifier: Modifier = Modifier,
) {
    TimelineOutlinedTextField(
        text = label,
        onTextChange = onLabelValueChange,
        errorMessage = errorMessage,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Words,
        ),
        singleLine = true,
    )
}

@Composable
private fun Actions(
    onAddClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedButton(
            onClick = onCancelClick,
            modifier = Modifier.weight(1f),
        ) {
            Text(text = stringResource(id = R.string.cancel))
        }

        Button(
            onClick = onAddClick,
            modifier = Modifier.weight(2f),
        ) {
            Text(
                text = stringResource(id = R.string.add_tag),
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
private fun ModifyTagFormPreview() {
    val form = ModifyTagForm.Initial

    TimelineTheme {
        Surface {
            ModifyTagForm(
                form = form,
                onLabelValueChange = {},
                onAddClick = {},
                onCancelClick = {},
            )
        }
    }
}
