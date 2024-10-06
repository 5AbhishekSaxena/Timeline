package `in`.developingdeveloper.timeline.eventlist.domain.datasource

import android.net.Uri
import androidx.core.net.toUri
import `in`.developingdeveloper.timeline.core.data.local.events.export.EventExporterRepository
import `in`.developingdeveloper.timeline.eventlist.domain.repositories.ExportDestinationRepository
import javax.inject.Inject

class DefaultEventExporterUseCase @Inject constructor(
    private val eventExporterRepository: EventExporterRepository,
    private val exportDestinationRepository: ExportDestinationRepository,
) : EventExporterUseCase {

    override suspend fun invoke(): Result<Unit> {
        val destinationFolderUri = getDestinationFolderUri()
            ?: return Result.failure(Exception("Destination folder uri is null"))

        return eventExporterRepository.export(destinationFolderUri)
    }

    private suspend fun getDestinationFolderUri(): Uri? {
        val destinationUri = exportDestinationRepository.getDestination()
        return destinationUri?.toUri()
    }
}
