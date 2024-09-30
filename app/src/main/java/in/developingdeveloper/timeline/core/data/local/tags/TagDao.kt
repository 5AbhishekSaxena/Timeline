package `in`.developingdeveloper.timeline.core.data.local.tags

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: PersistableTag)

    @Query("SELECT * FROM tags")
    fun getAllTags(): Flow<List<PersistableTag>>

    @Query("SELECT * FROM tags  WHERE tag_id = :tagId")
    suspend fun getTagById(tagId: String): PersistableTag?

    @Update
    suspend fun updateTag(tag: PersistableTag)

    @Transaction
    suspend fun deleteTag(tagId: String) {
        deleteEventTagCrossRefByTag(tagId)
        deleteTagById(tagId)
    }

    /**
     * This method should not be directly called.
     * Call [deleteTag] instead which internally calls [deleteEventTagCrossRefByTag]
     * in a transactional flow.
     */
    @Query("DELETE FROM event_tag_cross_ref WHERE tag_id = :tagId")
    suspend fun deleteEventTagCrossRefByTag(tagId: String)

    /**
     * This method should not be directly called.
     * Call [deleteTag] instead which internally calls [deleteTagById]
     * in a transactional flow.
     */
    @Query("DELETE FROM tags WHERE tag_id = :tagId")
    suspend fun deleteTagById(tagId: String)
}
