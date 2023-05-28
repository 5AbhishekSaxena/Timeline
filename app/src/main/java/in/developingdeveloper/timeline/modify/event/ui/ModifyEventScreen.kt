package `in`.developingdeveloper.timeline.modify.event.ui

import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import `in`.developingdeveloper.timeline.core.ui.components.onBackNavigationIconClick
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@Destination
fun ModifyEventScreen(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<String>,
    viewModel: ModifyEventViewModel = hiltViewModel(),
    eventId: String? = null,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        // Think of a better way to initialize the eventId.
        viewModel.init(eventId)
    }

    LaunchedEffect(key1 = viewState.isCompleted) {
        if (!viewState.isCompleted) return@LaunchedEffect

        resultNavigator.navigateBack(
            result = "Event added successfully!",
            onlyIfResumed = true,
        )
    }

    val errorMessage = viewState.errorMessage
    LaunchedEffect(key1 = errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    viewState.modifyTags.let { modifyTags ->
        LaunchedEffect(key1 = modifyTags) {
            if (!modifyTags) return@LaunchedEffect

            modalBottomSheetState.show()
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            hideModalBottomSheet(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
                onCompletion = viewModel::onModifyTagsCompleted,
            )
        }
    }

    ModifyEventContent(
        modalBottomSheetState = modalBottomSheetState,
        snackbarHostState = snackbarHostState,
        viewState = viewState,
        onTitleValueChange = viewModel::onTitleValueChange,
        onOccurredValueChange = viewModel::onOccurredValueChange,
        onModifyTagsClick = viewModel::onModifyTagsClick,
        onDoneClick = viewModel::onDoneClick,
        onNavigationIconClick = { onBackNavigationIconClick(navigator) },
        onCancelClick = { onCancelClick(navigator) },
        onBottomModalSheetDismiss = {
            hideModalBottomSheet(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
                onCompletion = viewModel::onModifyTagsCompleted,
            )
        },
        onTagClick = viewModel::onTagClick,
        onAddTagBottomSheetClick = {},
        onAddTagBottomSheetLabelValueChange = {},
        onAddTagFormBottomSheetClick = {},
        onCancelFormBottomSheetClick = {},
    )
}

private fun onCancelClick(navigator: DestinationsNavigator) {
    navigator.navigateUp()
}

private fun hideModalBottomSheet(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: SheetState,
    onCompletion: () -> Unit,
) {
    coroutineScope.launch {
        modalBottomSheetState.hide()
    }.invokeOnCompletion {
        if (!modalBottomSheetState.isVisible) {
            onCompletion()
        }
    }
}
