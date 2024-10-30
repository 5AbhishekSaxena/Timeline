package `in`.developingdeveloper.timeline.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.developingdeveloper.timeline.core.data.local.LocalPersistableDataSource
import `in`.developingdeveloper.timeline.core.data.local.data.store.DataStoreLocalPersistableDataSource
import `in`.developingdeveloper.timeline.core.utils.importer.events.DefaultExcelParser
import `in`.developingdeveloper.timeline.core.utils.importer.events.ExcelParser
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingDataModule {

    @Binds
    @Singleton
    abstract fun provideLocalPersistableDataSource(
        localPersistableDataSource: DataStoreLocalPersistableDataSource,
    ): LocalPersistableDataSource

    @Binds
    @Singleton
    abstract fun bindExcelParser(
        excelParser: DefaultExcelParser,
    ): ExcelParser
}
