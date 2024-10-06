package `in`.developingdeveloper.timeline.eventlist.domain.repositories

import `in`.developingdeveloper.timeline.core.data.local.LocalPersistableDataSource
import javax.inject.Inject

class DefaultExportDestinationRepository @Inject constructor(
    private val localPersistableDataSource: LocalPersistableDataSource,
) : ExportDestinationRepository {
    override suspend fun setDestination(uri: String) {
        localPersistableDataSource.save(EXPORT_DESTINATION_KEY, uri)
    }

    override suspend fun getDestination(): String? {
        return localPersistableDataSource.get(EXPORT_DESTINATION_KEY)
    }

    companion object {
        private const val EXPORT_DESTINATION_KEY = "export_location_destination"
    }
}
