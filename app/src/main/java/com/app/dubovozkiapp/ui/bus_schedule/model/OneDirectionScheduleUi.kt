package com.app.dubovozkiapp.ui.bus_schedule.model

data class OneDirectionScheduleUi(
    val topItemIndex: Int,
    val departedBusList: List<DepartedBusUi>,
    val busList: List<BusUi>
)