package `in`.developingdeveloper.timeline.modify.tag.domain.usecases

import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag

interface ModifyTagUseCase {
    suspend operator fun invoke(tag: Tag, isNewTag: Boolean): Result<Unit>
}
