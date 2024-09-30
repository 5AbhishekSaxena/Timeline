package `in`.developingdeveloper.timeline.modify.tag.domain.repositories

import `in`.developingdeveloper.timeline.core.data.local.tags.TagDataSource
import javax.inject.Inject

class DefaultDeleteTagRepository @Inject constructor(
    private val tagDataSource: TagDataSource,
) : DeleteTagRepository {
    override suspend fun deleteTagById(tagId: String) {
        tagDataSource.deleteTagById(tagId)
    }
}
