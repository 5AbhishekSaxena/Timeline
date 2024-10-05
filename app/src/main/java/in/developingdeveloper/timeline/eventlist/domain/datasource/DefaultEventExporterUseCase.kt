package `in`.developingdeveloper.timeline.eventlist.domain.datasource

import android.net.Uri
import androidx.core.net.toUri
import `in`.developingdeveloper.timeline.core.data.local.events.export.EventExporterService
import javax.inject.Inject
import kotlin.random.Random

class DefaultEventExporterUseCase @Inject constructor(
    private val eventExporterService: EventExporterService,
) : EventExporterUseCase {

    override suspend fun invoke(): Result<Unit> {
        val destinationFolderUri = getDestinationFolderUri()
            ?: return Result.failure(Exception("Destination folder uri is null"))

        return eventExporterService.export(destinationFolderUri)
    }

    private fun getDestinationFolderUri(): Uri? {
        val hasDestinationUri = Random.nextBoolean()
        return if (hasDestinationUri) {
            "content://com.android.externalstorage.documents/tree/primary%3AEvents".toUri()
        } else {
            null
        }
    }
}
