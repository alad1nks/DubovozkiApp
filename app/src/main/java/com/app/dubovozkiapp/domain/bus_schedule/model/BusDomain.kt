package com.app.dubovozkiapp.domain.bus_schedule.model

import com.app.dubovozkiapp.consts.Station
import com.app.dubovozkiapp.ui.bus_schedule.model.BusUi
import com.app.dubovozkiapp.ui.bus_schedule.model.StationUi


sealed interface BusDomain {
    fun toUi(): BusUi
    data class Today(
        val id: Int,
        val timeLeft: Long,
        val dayTimeString: String,
        val station: String
    ) : BusDomain {
        override fun toUi(): BusUi {
            val minutesLeft = this.timeLeft % 60
            val hoursLeft = this.timeLeft / 60
            return BusUi.Today(
                id = id,
                time = dayTimeString,
                timeLeft = if (hoursLeft > 0) {
                    "через $hoursLeft ч $minutesLeft мин"
                } else {
                    "через $minutesLeft мин"
                },
                timeLeftSoon = timeLeft <= 15,
                station = station.toStationUi()
            )
        }
    }

    data class TodayDeparted(
        val id: Int,
        val timePassed: Long,
        val dayTimeString: String,
        val station: String
    ) : BusDomain {
        override fun toUi(): BusUi {
            val minutesPassed = this.timePassed % 60
            val hoursPassed = this.timePassed / 60
            return BusUi.TodayDeparted(
                id = id,
                time = dayTimeString,
                timePassed = if (hoursPassed > 0) {
                    "$hoursPassed ч $minutesPassed мин назад"
                } else {
                    "$minutesPassed мин назад"
                },
                station = station.toStationUi()
            )
        }

    }

    data class NotToday(
        val id: Int,
        val dayTimeString: String,
        val station: String
    ) : BusDomain {
        override fun toUi(): BusUi {
            return BusUi.NotToday(
                id = id,
                time = dayTimeString,
                station = station.toStationUi()
            )
        }
    }

    fun String.toStationUi(): StationUi {
        return when(this) {
            Station.ODINTSOVO -> StationUi.Odintsovo
            Station.SLAVYANKA -> StationUi.Slavyanka
            else -> StationUi.Molodyozhnaya
        }
    }
}