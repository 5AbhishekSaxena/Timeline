package `in`.developingdeveloper.timeline.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import `in`.developingdeveloper.timeline.R
import `in`.developingdeveloper.timeline.core.ui.components.onBackNavigationIconClick
import `in`.developingdeveloper.timeline.core.ui.models.UiText
import `in`.developingdeveloper.timeline.destinations.TagListScreenDestination
import `in`.developingdeveloper.timeline.settings.ui.models.UiSetting

@Composable
@Destination
fun SettingsScreen(navigator: DestinationsNavigator) {
    val settings = remember { getUiSettings(navigator) }

    SettingsContent(
        settings = settings,
        onBackNavigationIconClick = { onBackNavigationIconClick(navigator) },
    )
}

private fun getUiSettings(navigator: DestinationsNavigator): List<UiSetting> {
    return listOf(
        UiSetting(
            UiText.ResourceText(value = R.string.tags),
            onClick = {
                navigator.navigate(TagListScreenDestination)
            },
        ),
    )
}
