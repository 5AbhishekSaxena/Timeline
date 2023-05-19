package `in`.developingdeveloper.timeline.modify.tag.domain.usecases

import android.util.Log
import `in`.developingdeveloper.timeline.core.domain.tags.models.Tag
import `in`.developingdeveloper.timeline.modify.tag.domain.exceptions.ModifyTagException
import `in`.developingdeveloper.timeline.modify.tag.domain.repositories.AddTagRepository
import `in`.developingdeveloper.timeline.modify.tag.domain.repositories.UpdateTagRepository
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught", "TooGenericExceptionThrown")
class DefaultModifyTagUseCase @Inject constructor(
    private val addTagRepository: AddTagRepository,
    private val updateTagRepository: UpdateTagRepository,
) : ModifyTagUseCase {
    override suspend operator fun invoke(tag: Tag, isNewTag: Boolean): Result<Unit> {
        return try {
            Log.e(javaClass.name, "invoke, tag: $tag, isNewTag: $isNewTag")
            validate(tag)

            if (isNewTag) {
                addTagRepository.addTag(tag)
            } else {
                updateTagRepository.updateTag(tag)
            }

            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    private fun validate(tag: Tag) {
        if (tag.id.isBlank()) throw ModifyTagException.InvalidIdException("Id is required.")

        if (tag.label.isBlank()) {
            throw ModifyTagException.InvalidLabelException("Label is required.")
        }
    }
}
