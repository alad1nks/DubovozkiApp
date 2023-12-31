package com.app.dubovozkiapp.domain.bus_schedule.factory

import com.app.dubovozkiapp.data.bus_schedule.entities.BusEntity
import com.app.dubovozkiapp.domain.bus_schedule.model.BusDomain
import java.util.Calendar

object BusScheduleDomainFactory {

    fun Pair<List<BusEntity>, List<BusEntity>>.toDomain(): Pair<List<BusDomain>, List<BusDomain>> {
        return Pair(this.first.toDomain(), this.second.toDomain())
    }


    private fun List<BusEntity>.toDomain(): List<BusDomain> {
        val currentTime = (Calendar.getInstance().timeInMillis + 10800000) % 86400000
        return this.map { bus ->
            val id = bus.id?.let { bus.id } ?: 0
            val timeLeft = ((bus.dayTime?.let { bus.dayTime } ?: 0L) - currentTime) / 60000
            val dayTimeString = bus.dayTimeString?.let { bus.dayTimeString } ?: ""
            val station = bus.station?.let { bus.station } ?: ""
            BusDomain(
                id = id,
                timeLeft = timeLeft,
                dayTimeString = dayTimeString,
                station = station
            )
        }
    }
}