package com.app.dubovozkiapp.ui.bus_schedule.factory

import com.app.dubovozkiapp.domain.bus_schedule.model.BusScheduleDomain
import com.app.dubovozkiapp.domain.bus_schedule.model.OneDirectionScheduleDomain
import com.app.dubovozkiapp.ui.bus_schedule.model.BusScheduleUi
import com.app.dubovozkiapp.ui.bus_schedule.model.DepartedBusUi
import com.app.dubovozkiapp.ui.bus_schedule.model.OneDirectionScheduleUi

object BusUiFactory {

    fun BusScheduleDomain.toUi(): BusScheduleUi {
        return BusScheduleUi(
            moscow = this.moscowSchedule.toUi(),
            dubki = this.dubkiSchedule.toUi()
        )
    }

    private fun OneDirectionScheduleDomain.toUi(): OneDirectionScheduleUi {
        return OneDirectionScheduleUi(
            topItemIndex = this.firstBus,
            departedBusList = this.departedBusList.map {
                val minutesPassed = it.timePassed % 60
                val hoursPassed = it.timePassed / 60
                DepartedBusUi(
                    id = it.id,
                    time = it.dayTimeString,
                    timeLeft = if (hoursPassed > 0) {
                        "$hoursPassed ч $minutesPassed мин назад"
                    } else {
                        "$minutesPassed мин назад"
                    },
                    station = it.station
                )
            },
            busList = this.busList.map {
                it.toUi()
            }
        )
    }
}