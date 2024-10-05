package `in`.developingdeveloper.timeline.eventlist.ui

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
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

    var exportEvents by remember { mutableStateOf(false) } // by remember { derivedStateOf { viewState.exportEvents } }

    var exportEventsUri by remember { mutableStateOf<Uri?>(null) }

    val contentResolver = context.contentResolver

    val exportLocationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree(),
        onResult = { uri ->
            onExportLocationLauncherResult(
                uri = uri,
                contentResolver = contentResolver,
                onFinished = {
                    exportEventsUri = uri
                    exportEvents = false
                    exportEvents = true
                },
            )
        },
    )

    LaunchedEffect(exportEvents) {
        if (!exportEvents) return@LaunchedEffect

        exportEvents(
            exportEventsUri = exportEventsUri,
            exportLocationLauncher = exportLocationLauncher,
            contentResolver = contentResolver,
            onFinished = {
                exportEvents = false
            },
        )
    }

    LaunchedEffect(key1 = viewState.errorMessage) {
        viewState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    EventListContent(
        viewState = viewState,
        onExportEventClick = { exportEvents = true },
        onEventListItemClick = { onEventListItemClick(navigator, it) },
        onAddEventClick = { onAddEventClick(navigator) },
        onSettingsClick = { onSettingsClick(navigator) },
        modifier = modifier,
    )
}

private fun onExportLocationLauncherResult(
    uri: Uri?,
    contentResolver: ContentResolver,
    onFinished: () -> Unit,
) {
    if (uri == null) return

    val flags =
        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

    contentResolver.takePersistableUriPermission(uri, flags)
    onFinished()
}

@SuppressWarnings("TooGenericExceptionCaught", "SwallowedException")
private fun exportEvents(
    exportEventsUri: Uri?,
    exportLocationLauncher: ManagedActivityResultLauncher<Uri?, Uri?>,
    contentResolver: ContentResolver,
    onFinished: () -> Unit,
) {
    try {
        if (exportEventsUri == null) {
            exportLocationLauncher.launch(null)
        }
        val documentUri = DocumentsContract.buildDocumentUriUsingTree(
            exportEventsUri,
            DocumentsContract.getTreeDocumentId(exportEventsUri),
        )

        val newFileUri = DocumentsContract.createDocument(
            contentResolver,
            documentUri,
            "text/plain",
            "event.txt",
        )

        if (newFileUri == null) return

        contentResolver.openOutputStream(newFileUri)?.use { out ->
            val content = "Hello World"
            out.write(content.toByteArray())
            out.flush()
        }
    } catch (exception: Exception) {
        // do something
    } finally {
        onFinished()
    }
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
