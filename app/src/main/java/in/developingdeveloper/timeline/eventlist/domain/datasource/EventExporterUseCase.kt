package `in`.developingdeveloper.timeline.eventlist.domain.datasource

interface EventExporterUseCase {

    operator fun invoke(): Result<Unit>
}
