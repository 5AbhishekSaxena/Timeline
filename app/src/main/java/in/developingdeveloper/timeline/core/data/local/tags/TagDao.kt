package `in`.developingdeveloper.timeline.core.data.local.tags

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
}
