package `in`.developingdeveloper.timeline.core.utils.export.excel

import android.net.Uri
import org.apache.poi.ss.usermodel.Workbook

interface EventFileExporter {
    suspend fun export(fileUri: Uri, workbook: Workbook): Result<String>
}
