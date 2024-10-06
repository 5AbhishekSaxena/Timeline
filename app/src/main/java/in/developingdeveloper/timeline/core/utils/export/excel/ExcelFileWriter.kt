package `in`.developingdeveloper.timeline.core.utils.export.excel

import org.apache.poi.ss.usermodel.Workbook

interface ExcelFileWriter {
    suspend fun write(data: Map<String, Array<String>>): Result<Workbook>
}
