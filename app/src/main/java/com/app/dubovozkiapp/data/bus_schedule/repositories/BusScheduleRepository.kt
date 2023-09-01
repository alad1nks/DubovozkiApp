package com.app.dubovozkiapp.data.bus_schedule.repositories

import com.app.dubovozkiapp.consts.RevisionResponse
import com.app.dubovozkiapp.data.bus_schedule.entities.BusEntity

interface BusScheduleRepository {

    suspend fun getBusList(
        day: Int,
        station: String
    ): Pair<List<BusEntity>, List<BusEntity>>

    suspend fun getBusListAllStation(
        day: Int
    ): Pair<List<BusEntity>, List<BusEntity>>

    suspend fun refreshBusSchedule(): RevisionResponse

}