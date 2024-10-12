package `in`.developingdeveloper.timeline.eventlist.domain.usescases

import android.net.Uri
import `in`.developingdeveloper.timeline.core.utils.importer.events.ImportEventTemplateGeneratorResult
import kotlinx.coroutines.flow.Flow

interface GenerateImportEventTemplateUseCase {
    operator fun invoke(fileUri: Uri): Flow<ImportEventTemplateGeneratorResult<String>>
}
