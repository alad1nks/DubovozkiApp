package com.app.dubovozkiapp.domain.bus_schedule.usecases

import com.app.dubovozkiapp.consts.RevisionResponse
import com.app.dubovozkiapp.data.bus_schedule.repositories.BusScheduleRepository
import javax.inject.Inject

class RefreshBusScheduleUseCaseImpl @Inject constructor(
    private val repository: BusScheduleRepository
) : RefreshBusScheduleUseCase {
    override suspend fun refreshBusSchedule(): RevisionResponse = repository.refreshBusSchedule()
}