package `in`.developingdeveloper.timeline.eventlist.ui

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import `in`.developingdeveloper.timeline.core.utils.ExcelConstants
import `in`.developingdeveloper.timeline.destinations.ModifyEventScreenDestination
import `in`.developingdeveloper.timeline.destinations.SettingsScreenDestination
import `in`.developingdeveloper.timeline.eventlist.ui.models.UIEventListItem

@Composable
@Destination
@RootNavGraph(start = true)
fun EventListScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: EventListViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val importEventsGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument(ExcelConstants.EXCEL_MIME_TYPE),
        onResult = { uri ->
            if (uri == null) return@rememberLauncherForActivityResult
            viewModel.onGenerateImportEventTemplateClick(uri)
        },
    )

    val exportLocationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree(),
        onResult = { uri ->
            if (uri == null) return@rememberLauncherForActivityResult

            val flags =
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

            context.contentResolver.takePersistableUriPermission(uri, flags)
            viewModel.exportEvents(uri)
        },
    )

    LaunchedEffect(viewState.requestForEventExportDestination) {
        if (!viewState.requestForEventExportDestination) return@LaunchedEffect

        exportLocationLauncher.launch(null)
        viewModel.onEventExportDestinationRequested()
    }

    LaunchedEffect(key1 = viewState.errorMessage) {
        viewState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(viewState.isImportingEvents) {
        if (viewState.isImportingEvents) return@LaunchedEffect
        viewModel.onIsImportingEventsCompleted()
    }

    EventListContent(
        viewState = viewState,
        onAlertMessageShown = viewModel::onAlertMessageShown,
        onImportEventClick = viewModel::onImportEventClick,
        onImportDialogDismiss = viewModel::dismissImportingEventsDialog,
        onImportDialogGenerateTemplateClick = {
            importEventsGalleryLauncher.launch("Import Events Template.xlsx")
        },
        onExportEventClick = viewModel::exportEvents,
        onEventListItemClick = { onEventListItemClick(navigator, it) },
        onAddEventClick = { onAddEventClick(navigator) },
        onSettingsClick = { onSettingsClick(navigator) },
        modifier = modifier,
    )
}

private fun onEventListItemClick(navigator: DestinationsNavigator, event: UIEventListItem) {
    if (event !is UIEventListItem.Event) return
    navigator.navigate(ModifyEventScreenDestination(eventId = event.id))
}

private fun onAddEventClick(navigator: DestinationsNavigator) {
    navigator.navigate(ModifyEventScreenDestination())
}

private fun onSettingsClick(navigator: DestinationsNavigator) {
    navigator.navigate(SettingsScreenDestination)
}
