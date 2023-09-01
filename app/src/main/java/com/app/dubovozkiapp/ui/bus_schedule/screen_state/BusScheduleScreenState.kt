package com.app.dubovozkiapp.ui.bus_schedule.screen_state

import com.app.dubovozkiapp.ui.bus_schedule.model.BusScheduleUi

sealed interface BusScheduleScreenState {
    object Init : BusScheduleScreenState

    data class Loading(
        val schedule: BusScheduleUi
    ) : BusScheduleScreenState

    data class Data(
        val schedule: BusScheduleUi
    ) : BusScheduleScreenState

    data class NetworkError(
        val schedule: BusScheduleUi
    ) : BusScheduleScreenState

    object DatabaseError : BusScheduleScreenState
}