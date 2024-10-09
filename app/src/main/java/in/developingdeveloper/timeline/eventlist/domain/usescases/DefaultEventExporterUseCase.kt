package `in`.developingdeveloper.timeline.eventlist.domain.usescases

import android.net.Uri
import androidx.core.net.toUri
import `in`.developingdeveloper.timeline.core.data.local.events.export.EventExporterRepository
import `in`.developingdeveloper.timeline.core.domain.event.models.Event
import `in`.developingdeveloper.timeline.core.utils.export.excel.EventExporterResult
import `in`.developingdeveloper.timeline.eventlist.domain.repositories.ExportDestinationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.io.FileNotFoundException
import javax.inject.Inject

class DefaultEventExporterUseCase @Inject constructor(
    private val eventExporterRepository: EventExporterRepository,
    private val exportDestinationRepository: ExportDestinationRepository,
) : EventExporterUseCase {

    override fun invoke(events: List<Event>): Flow<EventExporterResult<String>> = flow {
        emit(EventExporterResult.StatusUpdate("Checking destination folder."))
        val destinationFolderUri = getDestinationFolderUri()
        emitAll(eventExporterRepository.export(destinationFolderUri, events))
    }

    private suspend fun getDestinationFolderUri(): Uri {
        return exportDestinationRepository.getDestination()
            ?.toUri()
            ?: throw FileNotFoundException("Destination folder not set.")
    }
}
