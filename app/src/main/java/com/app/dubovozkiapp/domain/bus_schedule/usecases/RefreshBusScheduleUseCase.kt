package com.app.dubovozkiapp.domain.bus_schedule.usecases

import com.app.dubovozkiapp.consts.RevisionResponse

interface RefreshBusScheduleUseCase {
    suspend fun refreshBusSchedule(): RevisionResponse
}