package `in`.developingdeveloper.timeline.modify.tag.domain.usecases

interface DeleteTagUseCase {
    suspend operator fun invoke(tagId: String)
}
