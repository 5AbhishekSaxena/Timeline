package `in`.developingdeveloper.timeline.eventlist.domain.usescases

interface EventImporterUseCase {

    suspend operator fun invoke(fileUri: String)
}
