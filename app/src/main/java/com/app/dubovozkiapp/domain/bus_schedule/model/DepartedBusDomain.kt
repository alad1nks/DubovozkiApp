package com.app.dubovozkiapp.domain.bus_schedule.model

data class DepartedBusDomain(
    val id: Int,
    val timePassed: Long,
    val dayTimeString: String,
    val station: String
)