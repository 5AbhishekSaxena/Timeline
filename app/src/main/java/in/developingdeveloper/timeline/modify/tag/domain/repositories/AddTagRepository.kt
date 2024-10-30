package `in`.developingdeveloper.timeline.modify.tag.domain.repositories

import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag

interface AddTagRepository {
    suspend fun addTag(tag: Tag)
    suspend fun addTags(tags: List<Tag>)
}
