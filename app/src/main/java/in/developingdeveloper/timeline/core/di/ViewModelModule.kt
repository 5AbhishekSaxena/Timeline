package `in`.developingdeveloper.timeline.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import `in`.developingdeveloper.timeline.eventlist.domain.datasource.DefaultEventExporterUseCase
import `in`.developingdeveloper.timeline.eventlist.domain.datasource.EventExporterUseCase

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindEventExporterDataSource(
        eventExporterDataSource: DefaultEventExporterUseCase,
    ): EventExporterUseCase
}
