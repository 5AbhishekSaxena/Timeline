package `in`.developingdeveloper.timeline.eventlist.domain.usescases

import `in`.developingdeveloper.timeline.eventlist.domain.repositories.ExportDestinationRepository
import javax.inject.Inject

class DefaultSaveDestinationUriUseCase @Inject constructor(
    private val exportDestinationRepository: ExportDestinationRepository,
) : SaveDestinationUriUseCase {
    override suspend fun invoke(uri: String) {
        exportDestinationRepository.setDestination(uri)
    }
}
