package com.app.dubovozkiapp.consts

import com.app.dubovozkiapp.ui.bus_schedule.model.BusScheduleUi
import com.app.dubovozkiapp.ui.bus_schedule.screen_state.BusScheduleScreenState


sealed interface RevisionResponse {
    fun getScreenState(schedule: BusScheduleUi?): BusScheduleScreenState

    object NotEquals : RevisionResponse {
        override fun getScreenState(schedule: BusScheduleUi?): BusScheduleScreenState =
            schedule?.let { BusScheduleScreenState.Data(it) }
                ?: BusScheduleScreenState.DatabaseError
    }

    object Equals : RevisionResponse {
        override fun getScreenState(schedule: BusScheduleUi?): BusScheduleScreenState =
            schedule?.let { BusScheduleScreenState.Data(it) }
                ?: BusScheduleScreenState.DatabaseError
    }

    object NetworkError : RevisionResponse {
        override fun getScreenState(schedule: BusScheduleUi?): BusScheduleScreenState =
            schedule?.let { BusScheduleScreenState.NetworkError(it) }
                ?: BusScheduleScreenState.DatabaseError
    }
}