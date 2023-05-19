package `in`.developingdeveloper.timeline.settings.ui.models

import `in`.developingdeveloper.timeline.core.ui.models.UiText

data class UiSetting(
    val label: UiText,
    val onClick: () -> Unit,
)
