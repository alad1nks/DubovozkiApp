package com.app.dubovozkiapp.domain.bus_schedule.model

data class BusDomain(
    val id: Int,
    val timeLeft: Long,
    val dayTimeString: String,
    val station: String
)