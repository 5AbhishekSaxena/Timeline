package `in`.developingdeveloper.timeline.eventlist.domain.repositories

interface ExportDestinationRepository {
    suspend fun setDestination(uri: String)
    suspend fun getDestination(): String?
}
