package `in`.developingdeveloper.timeline.modify.tag.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import `in`.developingdeveloper.timeline.core.ui.components.onBackNavigationIconClick

@Composable
@Destination
fun ModifyTagScreen(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<String>,
    viewModel: ModifyTagViewModel = hiltViewModel(),
    tagId: String? = null,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        viewModel.init(tagId)
    }

    LaunchedEffect(viewState.isCompleted) {
        resultNavigator.navigateBack(
            result = "Tag added successfully!",
            onlyIfResumed = true,
        )
    }

    LaunchedEffect(viewState.isDeleted) {
        if (!viewState.isDeleted) return@LaunchedEffect

        resultNavigator.navigateBack(
            result = "Tag deleted successfully!",
            onlyIfResumed = true,
        )
    }

    val errorMessage = viewState.errorMessage
    LaunchedEffect(key1 = errorMessage) {
        if (errorMessage == null) return@LaunchedEffect

        snackbarHostState.showSnackbar(errorMessage)
    }

    ModifyTagContent(
        snackbarHostState = snackbarHostState,
        viewState = viewState,
        onNavigationIconClick = { onBackNavigationIconClick(navigator) },
        onLabelValueChange = viewModel::onLabelValueChange,
        onAddClick = {
            keyboardController?.hide()
            viewModel.onAddClick()
        },
        onCancelClick = { onCancelClick(navigator) },
        onDeleteClick = {
            keyboardController?.hide()
            viewModel.onDeleteClick()
        },
    )
}

private fun onCancelClick(navigator: DestinationsNavigator) {
    navigator.navigateUp()
}
