package `in`.developingdeveloper.timeline.modify.tag.ui.models

data class ModifyTagForm(
    val label: String,
    val labelErrorMessage: String?,
) {
    companion object {
        val Initial = ModifyTagForm(
            label = "",
            labelErrorMessage = null,
        )
    }
}
