package `in`.developingdeveloper.timeline.settings.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import `in`.developingdeveloper.timeline.R
import `in`.developingdeveloper.timeline.core.ui.components.BackNavigationIcon
import `in`.developingdeveloper.timeline.core.ui.components.TimelineStartAlignedTopAppBar
import `in`.developingdeveloper.timeline.core.ui.models.UiText
import `in`.developingdeveloper.timeline.core.ui.theme.TimelineTheme
import `in`.developingdeveloper.timeline.settings.ui.components.SettingsList
import `in`.developingdeveloper.timeline.settings.ui.models.UiSetting

@Composable
fun SettingsContent(
    settings: List<UiSetting>,
    onBackNavigationIconClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TimelineStartAlignedTopAppBar(
                title = stringResource(id = R.string.settings),
                navigationIcon = {
                    BackNavigationIcon(onBackNavigationIconClick)
                },
            )
        },
    ) { contentPadding ->
        SettingsList(
            settings = settings,
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
        )
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
private fun SettingsContentPreview() {
    val settings = (1..10).map {
        UiSetting(
            label = UiText.StringText("Label #$it"),
            onClick = {},
        )
    }

    TimelineTheme {
        Surface {
            SettingsContent(
                settings = settings,
                onBackNavigationIconClick = {},
            )
        }
    }
}
