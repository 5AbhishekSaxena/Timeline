package `in`.developingdeveloper.timeline.eventlist.domain.datasource

import android.net.Uri
import androidx.core.net.toUri
import `in`.developingdeveloper.timeline.core.data.local.events.export.EventExporterService
import `in`.developingdeveloper.timeline.eventlist.domain.repositories.ExportDestinationRepository
import javax.inject.Inject

class DefaultEventExporterUseCase @Inject constructor(
    private val eventExporterService: EventExporterService,
    private val exportDestinationRepository: ExportDestinationRepository,
) : EventExporterUseCase {

    override suspend fun invoke(): Result<Unit> {
        val destinationFolderUri = getDestinationFolderUri()
            ?: return Result.failure(Exception("Destination folder uri is null"))

        return eventExporterService.export(destinationFolderUri)
    }

    private suspend fun getDestinationFolderUri(): Uri? {
        val destinationUri = exportDestinationRepository.getDestination()
        return destinationUri?.toUri()
    }
}
