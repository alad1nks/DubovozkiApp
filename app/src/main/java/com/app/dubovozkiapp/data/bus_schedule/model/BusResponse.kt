package com.app.dubovozkiapp.data.bus_schedule.model

import com.app.dubovozkiapp.data.bus_schedule.entities.BusEntity

data class BusResponse(
    val id: Int? = null,
    val day: Int? = null,
    val dayTime: Long? = null,
    val dayTimeString: String? = null,
    val direction: String? = null,
    val station: String? = null
) {
    fun toEntity(): BusEntity {
        return BusEntity(
            id = id,
            day = day,
            dayTime = dayTime,
            dayTimeString = dayTimeString,
            direction = direction,
            station = station
        )
    }
}