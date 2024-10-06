package `in`.developingdeveloper.timeline.eventlist.domain.usescases

import android.net.Uri
import androidx.core.net.toUri
import `in`.developingdeveloper.timeline.core.data.local.events.export.EventExporterRepository
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.eventlist.domain.repositories.ExportDestinationRepository
import javax.inject.Inject

class DefaultEventExporterUseCase @Inject constructor(
    private val eventExporterRepository: EventExporterRepository,
    private val exportDestinationRepository: ExportDestinationRepository,
) : EventExporterUseCase {

    override suspend fun invoke(events: List<Event>): Result<Unit> {
        val destinationFolderUri = getDestinationFolderUri()
            ?: return Result.failure(Exception("Destination folder uri is null"))

        return eventExporterRepository.export(destinationFolderUri, events)
    }

    private suspend fun getDestinationFolderUri(): Uri? {
        val destinationUri = exportDestinationRepository.getDestination()
        return destinationUri?.toUri()
    }
}
