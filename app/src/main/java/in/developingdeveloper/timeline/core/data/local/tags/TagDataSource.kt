package `in`.developingdeveloper.timeline.core.data.local.tags

import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import kotlinx.coroutines.flow.Flow

interface TagDataSource {
    suspend fun insertTag(tag: Tag)
    fun getAllTags(): Flow<List<Tag>>
    suspend fun updateTag(tag: Tag)
}
