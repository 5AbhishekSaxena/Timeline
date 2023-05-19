package `in`.developingdeveloper.timeline.taglist.domain.usecases

import android.util.Log
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.core.domain.tags.repositories.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultGetAllTagsUseCase @Inject constructor(
    private val tagsRepository: TagRepository,
) : GetAllTagsUseCase {
    override fun invoke(): Flow<Result<List<Tag>>> {
        return tagsRepository.getAllTags()
            .map {
                Log.e(javaClass.name, "invoke, tags: $it")
                Result.success(it)
            }.catch {
                emit(Result.failure(it))
            }
    }
}
