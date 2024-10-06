package `in`.developingdeveloper.timeline.core.utils.export.excel

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import javax.inject.Inject

class DefaultExcelFileWriter @Inject constructor() : ExcelFileWriter {
    override suspend fun write(data: Map<String, Array<String>>): Result<Workbook> {
        return withContext(Dispatchers.IO) {
            try {
                val workbook = XSSFWorkbook()

                val sheet = workbook.createSheet("Events")

                data.keys.forEachIndexed { rowIndex, key ->
                    val row: Row = sheet.createRow(rowIndex)
                    val objArr = data[key]
                    objArr?.forEachIndexed { colIndex, value ->
                        val cell: Cell = row.createCell(colIndex)
                        cell.setCellValue(value)
                    }
                }
                Result.success(workbook)
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: IllegalArgumentException) {
                Result.failure(exception)
            }
        }
    }
}
