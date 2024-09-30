package `in`.developingdeveloper.timeline.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.developingdeveloper.timeline.core.data.local.tags.RoomTagDataSource
import `in`.developingdeveloper.timeline.core.data.local.tags.TagDataSource
import `in`.developingdeveloper.timeline.core.domain.tags.repositories.DefaultTagRepository
import `in`.developingdeveloper.timeline.core.domain.tags.repositories.TagRepository
import `in`.developingdeveloper.timeline.modify.tag.domain.repositories.AddTagRepository
import `in`.developingdeveloper.timeline.modify.tag.domain.repositories.DefaultAddTagRepository
import `in`.developingdeveloper.timeline.modify.tag.domain.repositories.DefaultDeleteTagRepository
import `in`.developingdeveloper.timeline.modify.tag.domain.repositories.DefaultUpdateTagRepository
import `in`.developingdeveloper.timeline.modify.tag.domain.repositories.DeleteTagRepository
import `in`.developingdeveloper.timeline.modify.tag.domain.repositories.UpdateTagRepository
import `in`.developingdeveloper.timeline.modify.tag.domain.usecases.DefaultDeleteTagUseCase
import `in`.developingdeveloper.timeline.modify.tag.domain.usecases.DefaultGetTagByIdUseCase
import `in`.developingdeveloper.timeline.modify.tag.domain.usecases.DefaultModifyTagUseCase
import `in`.developingdeveloper.timeline.modify.tag.domain.usecases.DeleteTagUseCase
import `in`.developingdeveloper.timeline.modify.tag.domain.usecases.GetTagByIdUseCase
import `in`.developingdeveloper.timeline.modify.tag.domain.usecases.ModifyTagUseCase
import `in`.developingdeveloper.timeline.taglist.domain.usecases.DefaultGetAllTagsUseCase
import `in`.developingdeveloper.timeline.taglist.domain.usecases.GetAllTagsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TagsModule {

    @Binds
    @Singleton
    abstract fun bindTagRepository(
        tagRepository: DefaultTagRepository,
    ): TagRepository

    @Binds
    @Singleton
    abstract fun bindGetAllTagsUseCase(
        getAllTagsUseCase: DefaultGetAllTagsUseCase,
    ): GetAllTagsUseCase

    @Binds
    @Singleton
    abstract fun bindGetTagByIdUseCase(
        getTagByIdUseCase: DefaultGetTagByIdUseCase,
    ): GetTagByIdUseCase

    @Binds
    @Singleton
    abstract fun bindTagDataSource(
        roomTagDataSource: RoomTagDataSource,
    ): TagDataSource

    @Binds
    @Singleton
    abstract fun bindAddTagRepository(
        addTagRepository: DefaultAddTagRepository,
    ): AddTagRepository

    @Binds
    @Singleton
    abstract fun bindUpdateTagRepository(
        updateTagRepository: DefaultUpdateTagRepository,
    ): UpdateTagRepository

    @Binds
    @Singleton
    abstract fun bindModifyTagUseCase(
        addTagUseCase: DefaultModifyTagUseCase,
    ): ModifyTagUseCase

    @Binds
    @Singleton
    abstract fun bindDeleteTagRepository(
        deleteTagRepository: DefaultDeleteTagRepository,
    ): DeleteTagRepository

    @Binds
    @Singleton
    abstract fun bindDeleteTagUseCase(
        deleteTagUseCase: DefaultDeleteTagUseCase,
    ): DeleteTagUseCase
}
