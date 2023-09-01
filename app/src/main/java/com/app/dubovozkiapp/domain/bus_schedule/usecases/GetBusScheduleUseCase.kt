package com.app.dubovozkiapp.domain.bus_schedule.usecases

import com.app.dubovozkiapp.domain.bus_schedule.model.BusScheduleDomain

interface GetBusScheduleUseCase {
    suspend fun getBusSchedule(day: String, station: String): BusScheduleDomain
}