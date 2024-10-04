package `in`.developingdeveloper.timeline.modify.event.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import `in`.developingdeveloper.timeline.R
import `in`.developingdeveloper.timeline.core.ui.components.FormInput
import `in`.developingdeveloper.timeline.core.ui.components.TimelineOutlinedTextField
import `in`.developingdeveloper.timeline.core.utils.formatDateForUI
import `in`.developingdeveloper.timeline.core.utils.toEpochMilli
import `in`.developingdeveloper.timeline.core.utils.toLocalDate
import `in`.developingdeveloper.timeline.core.utils.toLocalDateTime
import java.time.LocalDateTime

@Composable
fun OccurredOnInput(
    value: LocalDateTime,
    errorMessage: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val occurredOnInteractionSource = remember { MutableInteractionSource() }
    val occurredInteractionSourceState by occurredOnInteractionSource.collectIsPressedAsState()

    var openDatePickerDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = occurredInteractionSourceState) {
        if (occurredInteractionSourceState && !openDatePickerDialog) openDatePickerDialog = true
    }

    if (openDatePickerDialog) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = value.toEpochMilli(),
        )

        OccurredOnDatePickerDialog(
            datePickerState = datePickerState,
            onDismiss = { openDatePickerDialog = false },
            onConfirm = {
                openDatePickerDialog = false
                val selectedDateInMillis =
                    datePickerState.selectedDateMillis ?: return@OccurredOnDatePickerDialog

                val selectedDate = selectedDateInMillis.toLocalDate()
                onValueChange(selectedDate.toLocalDateTime().toString())
            },
        )
    }

    FormInput(
        label = stringResource(id = R.string.occurred_on),
        input = {
            OccurredOnInputField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                errorMessage = errorMessage,
                openDatePickerDialog = openDatePickerDialog,
                occurredOnInteractionSource = occurredOnInteractionSource,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun OccurredOnInputField(
    value: LocalDateTime,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    errorMessage: String?,
    openDatePickerDialog: Boolean,
    occurredOnInteractionSource: MutableInteractionSource,
) {
    var openDatePickerDialog1 = openDatePickerDialog
    TimelineOutlinedTextField(
        text = value.formatDateForUI(),
        onTextChange = onValueChange,
        enabled = enabled,
        errorMessage = errorMessage,
        trailingIcon = {
            IconButton(
                onClick = {
                    if (!openDatePickerDialog1) {
                        openDatePickerDialog1 = true
                    }
                },
            ) {
                Icon(imageVector = Icons.Default.Event, contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
        ),
        singleLine = true,
        readOnly = true,
        interactionSource = occurredOnInteractionSource,
        modifier = Modifier.focusProperties { canFocus = false },
    )
}
