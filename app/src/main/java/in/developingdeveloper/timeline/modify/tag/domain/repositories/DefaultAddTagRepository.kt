package `in`.developingdeveloper.timeline.modify.tag.domain.repositories

import `in`.developingdeveloper.timeline.core.data.local.tags.TagDataSource
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import javax.inject.Inject

class DefaultAddTagRepository @Inject constructor(
    private val tagDataSource: TagDataSource,
) : AddTagRepository {
    override suspend fun addTag(tag: Tag) {
        tagDataSource.insertTag(tag)
    }

    override suspend fun addTags(tags: List<Tag>) {
        tagDataSource.insertTags(tags)
    }
}
