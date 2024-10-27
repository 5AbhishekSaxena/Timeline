package `in`.developingdeveloper.timeline.core.utils.importer.events

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import javax.inject.Inject

class ExcelParser @Inject constructor(
    @ApplicationContext
    private val context: Context,
) {
    suspend fun parse(fileUri: Uri) = withContext(Dispatchers.IO) {
        val parsedEvents = mutableListOf<ParsedEvent>()
        context.contentResolver.openInputStream(fileUri)?.use {
            val workbook = WorkbookFactory.create(it)
            val sheet = workbook.getSheetAt(0)
            val rowIterator = sheet.iterator().withIndex()

            while (rowIterator.hasNext()) {
                val (rowIndex, row: Row?) = rowIterator.next()
                if (rowIndex == 0 || row == null) continue

                parsedEvents.add(
                    ParsedEvent(
                        serialNumber = row.getCell(SERIAL_NUMBER_COLUMN_INDEX)?.toString().orEmpty(),
                        id = row.getCell(ID_COLUMN_INDEX)?.toString().orEmpty(),
                        title = row.getCell(TITLE_COLUMN_INDEX)?.toString().orEmpty(),
                        occurredOn = row.getCell(OCCURRED_ON_COLUMN_INDEX)?.toString().orEmpty(),
                        tags = row.getCell(TAGS_COLUMN_INDEX)?.toString().orEmpty(),
                    ),
                )
            }
        }
        parsedEvents
    }

    companion object {
        private const val SERIAL_NUMBER_COLUMN_INDEX = 0
        private const val ID_COLUMN_INDEX = 1
        private const val TITLE_COLUMN_INDEX = 2
        private const val OCCURRED_ON_COLUMN_INDEX = 3
        private const val TAGS_COLUMN_INDEX = 4
    }
}
