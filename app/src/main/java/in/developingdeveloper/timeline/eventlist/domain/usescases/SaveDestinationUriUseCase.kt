package `in`.developingdeveloper.timeline.eventlist.domain.usescases

interface SaveDestinationUriUseCase {
    suspend operator fun invoke(uri: String)
}
