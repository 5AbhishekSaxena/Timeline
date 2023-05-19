package `in`.developingdeveloper.timeline.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import `in`.developingdeveloper.timeline.R

@Composable
fun BackNavigationIcon(onNavigationIconClick: () -> Unit) {
    IconButton(onClick = onNavigationIconClick) {
        Icon(
            imageVector = Icons.Default.NavigateBefore,
            contentDescription = stringResource(id = R.string.navigate_back),
        )
    }
}

fun onBackNavigationIconClick(navigator: DestinationsNavigator) {
    navigator.navigateUp()
}
