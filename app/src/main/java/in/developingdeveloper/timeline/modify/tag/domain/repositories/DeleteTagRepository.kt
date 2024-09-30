package `in`.developingdeveloper.timeline.modify.tag.domain.repositories

interface DeleteTagRepository {
    suspend fun deleteTagById(tagId: String)
}
