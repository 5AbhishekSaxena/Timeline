package `in`.developingdeveloper.timeline.core.data.local.tags

import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomTagDataSource @Inject constructor(
    private val tagDao: TagDao,
) : TagDataSource {
    override suspend fun insertTag(tag: Tag) {
        tagDao.insertTag(tag.toPersistableTag())
    }

    override fun getAllTags(): Flow<List<Tag>> {
        return tagDao.getAllTags()
            .map(List<PersistableTag>::toTags)
    }

    override suspend fun getTagById(tagId: String): Tag? {
        return tagDao.getTagById(tagId)?.toTag()
    }

    override suspend fun updateTag(tag: Tag) {
        val persistableTag = tag.toPersistableTag()
        tagDao.updateTag(persistableTag)
    }

    override suspend fun deleteTagById(tagId: String) {
        tagDao.deleteTag(tagId)
    }
}

private fun List<PersistableTag>.toTags(): List<Tag> {
    return this.map(PersistableTag::toTag)
}

private fun PersistableTag.toTag(): Tag {
    return Tag(
        id = this.id,
        label = this.label,
    )
}

private fun Tag.toPersistableTag(): PersistableTag {
    return PersistableTag(
        id = this.id,
        label = this.label,
    )
}
