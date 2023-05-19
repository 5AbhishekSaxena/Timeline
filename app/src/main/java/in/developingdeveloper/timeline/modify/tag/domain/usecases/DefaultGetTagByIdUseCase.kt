package `in`.developingdeveloper.timeline.modify.tag.domain.usecases

import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.core.domain.tags.repositories.TagRepository
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class DefaultGetTagByIdUseCase @Inject constructor(
    private val tagRepository: TagRepository,
) : GetTagByIdUseCase {
    override suspend fun invoke(tagId: String): Result<Tag> {
        return try {
            val tag = tagRepository.getTagById(tagId)
            Result.success(tag)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
