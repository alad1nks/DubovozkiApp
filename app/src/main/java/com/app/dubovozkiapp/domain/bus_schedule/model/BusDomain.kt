package com.app.dubovozkiapp.domain.bus_schedule.model

import com.app.dubovozkiapp.ui.bus_schedule.model.BusUi


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
                station = station
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
                station = station
            )
        }
    }
}