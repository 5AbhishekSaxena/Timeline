package `in`.developingdeveloper.timeline.modify.tag.domain.usecases

import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag

interface GetTagByIdUseCase {
    suspend operator fun invoke(tagId: String): Result<Tag>
}
