package `in`.developingdeveloper.timeline.core.domain.tags.repositories

import `in`.developingdeveloper.timeline.core.data.local.tags.TagDataSource
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.core.exceptions.TimelineException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultTagRepository @Inject constructor(
    private val tagDataSource: TagDataSource,
) : TagRepository {

    override fun getAllTags(): Flow<List<Tag>> {
        return tagDataSource.getAllTags()
    }

    override suspend fun getTagById(tagId: String): Tag {
        return tagDataSource.getTagById(tagId) ?: throw TimelineException("Tag with id $tagId not found.")
    }

    override suspend fun getTagsByLabels(labels: List<String>): List<Tag> {
        return tagDataSource.getTagsByLabels(labels)
    }
}
