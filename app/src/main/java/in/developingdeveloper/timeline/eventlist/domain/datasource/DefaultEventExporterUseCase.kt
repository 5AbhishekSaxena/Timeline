package `in`.developingdeveloper.timeline.eventlist.domain.datasource

import android.net.Uri
import `in`.developingdeveloper.timeline.core.data.local.events.export.EventExporterService
import javax.inject.Inject

class DefaultEventExporterUseCase @Inject constructor(
    private val eventExporterService: EventExporterService,
) : EventExporterUseCase {

    override fun invoke(
        destinationFolderUri: Uri,
        onError: (Exception) -> Unit,
        onFinished: () -> Unit,
    ) {
        eventExporterService.export(destinationFolderUri, onError, onFinished)
    }
}
