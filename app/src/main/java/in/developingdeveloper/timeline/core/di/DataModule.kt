package `in`.developingdeveloper.timeline.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.developingdeveloper.timeline.core.data.local.events.EventDao
import `in`.developingdeveloper.timeline.core.data.local.room.TimelineDatabase
import `in`.developingdeveloper.timeline.core.data.local.tags.TagDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideTimelineDatabase(
        @ApplicationContext context: Context,
    ): TimelineDatabase {
        return TimelineDatabase.build(context)
    }

    @Provides
    @Singleton
    fun provideTagDao(
        timelineDatabase: TimelineDatabase,
    ): TagDao {
        return timelineDatabase.tagDao()
    }

    @Provides
    @Singleton
    fun provideEventDao(
        timelineDatabase: TimelineDatabase,
    ): EventDao {
        return timelineDatabase.eventDao()
    }
}
