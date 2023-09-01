package com.app.dubovozkiapp.ui.bus_schedule.model

data class BusUi(
    val id: Int,
    val time: String,
    val timeLeft: String,
    val timeLeftSoon: Boolean,
    val station: String
)