package com.app.dubovozkiapp.domain.bus_schedule.factory

import com.app.dubovozkiapp.data.bus_schedule.entities.BusEntity
import com.app.dubovozkiapp.domain.bus_schedule.model.BusDomain
import java.util.Calendar

object BusScheduleDomainFactory {

    fun Pair<List<BusEntity>, List<BusEntity>>.toDomain(): Pair<List<BusDomain.Today>, List<BusDomain.Today>> {
        return Pair(this.first.toDomainWithTime(), this.second.toDomainWithTime())
    }

    fun List<BusEntity>.toDomain(): List<BusDomain> {
        return this.map { bus ->
            val id = bus.id?.let { bus.id } ?: 0
            val dayTimeString = bus.dayTimeString?.let { bus.dayTimeString } ?: ""
            val station = bus.station?.let { bus.station } ?: ""
            BusDomain.NotToday(
                id = id,
                dayTimeString = dayTimeString,
                station = station
            )
        }
    }

    private fun List<BusEntity>.toDomainWithTime(): List<BusDomain.Today> {
        val currentTime = (Calendar.getInstance().timeInMillis + 10800000) % 86400000
        return this.map { bus ->
            val id = bus.id?.let { bus.id } ?: 0
            val timeLeft = ((bus.dayTime?.let { bus.dayTime } ?: 0L) - currentTime) / 60000
            val dayTimeString = bus.dayTimeString?.let { bus.dayTimeString } ?: ""
            val station = bus.station?.let { bus.station } ?: ""
            BusDomain.Today(
                id = id,
                timeLeft = timeLeft,
                dayTimeString = dayTimeString,
                station = station
            )
        }
    }
}