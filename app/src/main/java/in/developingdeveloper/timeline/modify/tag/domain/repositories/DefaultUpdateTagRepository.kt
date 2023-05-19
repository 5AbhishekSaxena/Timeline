package `in`.developingdeveloper.timeline.modify.tag.domain.repositories

import `in`.developingdeveloper.timeline.core.data.local.tags.TagDataSource
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import javax.inject.Inject

class DefaultUpdateTagRepository @Inject constructor(
    private val tagDataSource: TagDataSource,
) : UpdateTagRepository {
    override suspend fun updateTag(tag: Tag) {
        tagDataSource.updateTag(tag)
    }
}
