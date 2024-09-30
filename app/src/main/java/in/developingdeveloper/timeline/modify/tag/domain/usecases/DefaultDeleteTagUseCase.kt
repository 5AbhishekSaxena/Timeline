package `in`.developingdeveloper.timeline.modify.tag.domain.usecases

import `in`.developingdeveloper.timeline.modify.tag.domain.repositories.DeleteTagRepository
import javax.inject.Inject

class DefaultDeleteTagUseCase @Inject constructor(
    private val tagRepository: DeleteTagRepository,
) : DeleteTagUseCase {
    override suspend operator fun invoke(tagId: String) {
        tagRepository.deleteTagById(tagId)
    }
}
