package `in`.developingdeveloper.timeline.modify.tag.domain.repositories

import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag

interface UpdateTagRepository {
    suspend fun updateTag(tag: Tag)
}
