package com.app.dubovozkiapp.ui.bus_schedule.factory

import com.app.dubovozkiapp.domain.bus_schedule.model.BusScheduleDomain
import com.app.dubovozkiapp.domain.bus_schedule.model.OneDirectionScheduleDomain
import com.app.dubovozkiapp.ui.bus_schedule.model.BusScheduleUi
import com.app.dubovozkiapp.ui.bus_schedule.model.OneDirectionScheduleUi

object BusScheduleUiFactory {

    fun BusScheduleDomain.toUi(): BusScheduleUi {
        return BusScheduleUi(
            moscow = moscowSchedule.toUi(),
            dubki = dubkiSchedule.toUi()
        )
    }

    private fun OneDirectionScheduleDomain.toUi(): OneDirectionScheduleUi {
        return OneDirectionScheduleUi(
            topItemIndex = this.firstBus,
            busList = this.busList.map {
                it.toUi()
            }
        )
    }
}