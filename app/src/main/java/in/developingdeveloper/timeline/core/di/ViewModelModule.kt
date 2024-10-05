package `in`.developingdeveloper.timeline.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import `in`.developingdeveloper.timeline.eventlist.domain.datasource.DefaultEventExporterUseCase
import `in`.developingdeveloper.timeline.eventlist.domain.datasource.EventExporterUseCase
import `in`.developingdeveloper.timeline.eventlist.domain.repositories.DefaultExportDestinationRepository
import `in`.developingdeveloper.timeline.eventlist.domain.repositories.ExportDestinationRepository
import `in`.developingdeveloper.timeline.eventlist.domain.usescases.DefaultSaveDestinationUriUseCase
import `in`.developingdeveloper.timeline.eventlist.domain.usescases.SaveDestinationUriUseCase

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindEventExporterDataSource(
        eventExporterDataSource: DefaultEventExporterUseCase,
    ): EventExporterUseCase

    @Binds
    abstract fun bindExportDestinationRepository(
        exportDestinationRepository: DefaultExportDestinationRepository,
    ): ExportDestinationRepository

    @Binds
    abstract fun bindSaveDestinationUriUseCase(
        saveDestinationUriUseCase: DefaultSaveDestinationUriUseCase,
    ): SaveDestinationUriUseCase
}
