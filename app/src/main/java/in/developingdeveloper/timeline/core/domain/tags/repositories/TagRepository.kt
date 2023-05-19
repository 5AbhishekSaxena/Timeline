package `in`.developingdeveloper.timeline.core.domain.tags.repositories

import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {

    fun getAllTags(): Flow<List<Tag>>
    suspend fun getTagById(tagId: String): Tag
}
