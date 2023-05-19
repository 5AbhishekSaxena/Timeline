package `in`.developingdeveloper.timeline.taglist.domain.usecases

import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import kotlinx.coroutines.flow.Flow

interface GetAllTagsUseCase {
    operator fun invoke(): Flow<Result<List<Tag>>>
}
