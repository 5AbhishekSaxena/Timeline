package `in`.developingdeveloper.timeline.eventlist.domain.usescases

import android.net.Uri
import `in`.developingdeveloper.timeline.core.utils.importer.events.ImportEventTemplateGenerator
import `in`.developingdeveloper.timeline.core.utils.importer.events.ImportEventTemplateGeneratorResult
import kotlinx.coroutines.flow.Flow

class DefaultGenerateImportEventTemplateUseCase(
    private val templateGenerator: ImportEventTemplateGenerator,
) : GenerateImportEventTemplateUseCase {
    override fun invoke(fileUri: Uri): Flow<ImportEventTemplateGeneratorResult<String>> {
        return templateGenerator.generate(fileUri)
    }
}
