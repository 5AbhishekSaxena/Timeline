package `in`.developingdeveloper.timeline.modify.event.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import `in`.developingdeveloper.timeline.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun OccurredOnDatePickerDialog(
    datePickerState: DatePickerState,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm,
            ) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
    ) {
        OccurredOnDatePicker(datePickerState = datePickerState)
    }
}

@Composable
private fun OccurredOnDatePicker(datePickerState: DatePickerState) {
    DatePicker(
        state = datePickerState,
        dateValidator = { utcDateInMills ->
            val selectableDate =
                Instant
                    .ofEpochMilli(utcDateInMills)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()

            val tomorrow = LocalDate.now().plusDays(1)
            selectableDate.isBefore(tomorrow)
        },
    )
}
