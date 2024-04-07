package `in`.developingdeveloper.timeline.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.developingdeveloper.timeline.core.data.local.events.RoomEventsDataSource
import `in`.developingdeveloper.timeline.eventlist.domain.datasource.EventsDataSource
import `in`.developingdeveloper.timeline.eventlist.domain.repositories.DefaultEventListRepository
import `in`.developingdeveloper.timeline.eventlist.domain.repositories.EventListRepository
import `in`.developingdeveloper.timeline.eventlist.domain.usescases.DefaultGetAllEventsUseCase
import `in`.developingdeveloper.timeline.eventlist.domain.usescases.GetAllEventsUseCase
import `in`.developingdeveloper.timeline.modify.event.domain.repositories.AddEventRepository
import `in`.developingdeveloper.timeline.modify.event.domain.repositories.DefaultAddEventRepository
import `in`.developingdeveloper.timeline.modify.event.domain.repositories.DefaultDeleteEventRepository
import `in`.developingdeveloper.timeline.modify.event.domain.repositories.DefaultUpdateEventRepository
import `in`.developingdeveloper.timeline.modify.event.domain.repositories.DeleteEventRepository
import `in`.developingdeveloper.timeline.modify.event.domain.repositories.UpdateEventRepository
import `in`.developingdeveloper.timeline.modify.event.domain.usecases.DefaultDeleteEventUseCase
import `in`.developingdeveloper.timeline.modify.event.domain.usecases.DefaultGetEventByIdUseCase
import `in`.developingdeveloper.timeline.modify.event.domain.usecases.DefaultModifyEventUseCase
import `in`.developingdeveloper.timeline.modify.event.domain.usecases.DeleteEventUseCase
import `in`.developingdeveloper.timeline.modify.event.domain.usecases.GetEventByIdUseCase
import `in`.developingdeveloper.timeline.modify.event.domain.usecases.ModifyEventUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EventListModule {

    @Binds
    @Singleton
    abstract fun bindEventListRepository(
        eventListRepository: DefaultEventListRepository,
    ): EventListRepository

    @Binds
    @Singleton
    abstract fun bindEventListDataSource(
        eventsDataSource: RoomEventsDataSource,
    ): EventsDataSource

    @Binds
    @Singleton
    abstract fun bindGetAllEventsUseCase(
        getAllEventsUseCase: DefaultGetAllEventsUseCase,
    ): GetAllEventsUseCase

    @Binds
    @Singleton
    abstract fun bindGetEventByIdUseCase(
        getEventByIdUseCase: DefaultGetEventByIdUseCase,
    ): GetEventByIdUseCase

    @Binds
    @Singleton
    abstract fun bindAddEventRepository(
        addEventRepository: DefaultAddEventRepository,
    ): AddEventRepository

    @Binds
    @Singleton
    abstract fun bindUpdateEventRepository(
        updateEventRepository: DefaultUpdateEventRepository,
    ): UpdateEventRepository

    @Binds
    @Singleton
    abstract fun bindModifyEventUseCase(
        modifyEventUseCase: DefaultModifyEventUseCase,
    ): ModifyEventUseCase

    @Binds
    @Singleton
    abstract fun bindDeleteEventUseCase(
        deleteEventUseCase: DefaultDeleteEventUseCase,
    ): DeleteEventUseCase

    @Binds
    @Singleton
    abstract fun deleteEventRepository(
        deleteEventRepository: DefaultDeleteEventRepository,
    ): DeleteEventRepository
}
