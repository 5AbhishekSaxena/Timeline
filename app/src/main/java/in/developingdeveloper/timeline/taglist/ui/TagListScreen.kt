package `in`.developingdeveloper.timeline.taglist.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import `in`.developingdeveloper.timeline.core.ui.components.onBackNavigationIconClick
import `in`.developingdeveloper.timeline.destinations.ModifyTagScreenDestination
import `in`.developingdeveloper.timeline.taglist.ui.models.UITag
import kotlinx.coroutines.launch

@Composable
@Destination
fun TagListScreen(
    navigator: DestinationsNavigator,
    addTagResultRecipient: ResultRecipient<ModifyTagScreenDestination, String>,
    viewModel: TagListViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    addTagResultRecipient.onNavResult {
        when (it) {
            is NavResult.Canceled -> Unit
            is NavResult.Value -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(it.value)
                }
            }
        }
    }

    TagListContent(
        snackbarHostState = snackbarHostState,
        viewState = viewState,
        onNavigationIconClick = { onBackNavigationIconClick(navigator) },
        onTagListItemClick = { onTagListItemClick(navigator, it) },
        onAddTagClick = { onAddTagClick(navigator) },
    )
}

fun onTagListItemClick(navigator: DestinationsNavigator, tag: UITag) {
    navigator.navigate(ModifyTagScreenDestination(tag.id))
}

private fun onAddTagClick(navigator: DestinationsNavigator) {
    navigator.navigate(ModifyTagScreenDestination())
}
