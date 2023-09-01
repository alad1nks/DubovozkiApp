package com.app.dubovozkiapp.di

import com.app.dubovozkiapp.data.bus_schedule.repositories.BusScheduleRepository
import com.app.dubovozkiapp.data.bus_schedule.repositories.BusScheduleRepositoryImpl
import com.app.dubovozkiapp.domain.bus_schedule.usecases.GetBusScheduleUseCase
import com.app.dubovozkiapp.domain.bus_schedule.usecases.GetBusScheduleUseCaseImpl
import com.app.dubovozkiapp.domain.bus_schedule.usecases.RefreshBusScheduleUseCase
import com.app.dubovozkiapp.domain.bus_schedule.usecases.RefreshBusScheduleUseCaseImpl
import com.app.dubovozkiapp.storage.SharedPreferencesStorage
import com.app.dubovozkiapp.storage.Storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppBindsModule {

    @Singleton
    @Binds
    abstract fun bindStorage(storage: SharedPreferencesStorage): Storage

    @Singleton
    @Binds
    abstract fun bindBusScheduleRepository(repo: BusScheduleRepositoryImpl): BusScheduleRepository

    @Singleton
    @Binds
    abstract fun bindGetBusScheduleUseCase(useCase: GetBusScheduleUseCaseImpl): GetBusScheduleUseCase

    @Singleton
    @Binds
    abstract fun bindRefreshBusScheduleUseCase(useCase: RefreshBusScheduleUseCaseImpl): RefreshBusScheduleUseCase
}