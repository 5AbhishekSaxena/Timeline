package `in`.developingdeveloper.timeline.core.data.local.tags

import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import kotlinx.coroutines.flow.Flow

interface TagDataSource {
    suspend fun insertTag(tag: Tag)
    suspend fun insertTags(tags: List<Tag>)
    fun getAllTags(): Flow<List<Tag>>
    suspend fun getTagById(tagId: String): Tag?
    suspend fun getTagsByLabels(labels: List<String>): List<Tag>
    suspend fun updateTag(tag: Tag)
    suspend fun deleteTagById(tagId: String)
}
