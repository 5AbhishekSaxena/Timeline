package `in`.developingdeveloper.timeline.eventlist.domain.datasource

interface EventExporterUseCase {

    suspend operator fun invoke(): Result<Unit>
}
