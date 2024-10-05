package `in`.developingdeveloper.timeline.eventlist.domain.datasource

import android.net.Uri

interface EventExporterUseCase {

    operator fun invoke(
        destinationFolderUri: Uri,
        onError: (Exception) -> Unit,
        onFinished: () -> Unit,
    )
}
