package `in`.developingdeveloper.timeline.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import `in`.developingdeveloper.timeline.core.utils.export.excel.DefaultEventFileExporter
import `in`.developingdeveloper.timeline.core.utils.export.excel.DefaultExcelFileWriter
import `in`.developingdeveloper.timeline.core.utils.export.excel.EventFileExporter
import `in`.developingdeveloper.timeline.core.utils.export.excel.EventsExporter
import `in`.developingdeveloper.timeline.core.utils.export.excel.ExcelEventsExporter
import `in`.developingdeveloper.timeline.core.utils.export.excel.ExcelFileWriter

@Module
@InstallIn(ViewModelComponent::class)
abstract class EventExporterModule {

    @Binds
    abstract fun bindEventsExporter(
        eventsExporter: ExcelEventsExporter,
    ): EventsExporter

    @Binds
    abstract fun bindEventFileExporter(
        eventFileExporter: DefaultEventFileExporter,
    ): EventFileExporter

    @Binds
    abstract fun bindEventExporter(
        eventExporter: DefaultExcelFileWriter,
    ): ExcelFileWriter
}
