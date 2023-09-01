package com.app.dubovozkiapp.ui.bus_schedule.screen_state

import com.app.dubovozkiapp.ui.views.MenuItem
import com.app.dubovozkiapp.ui.views.TabItem

data class BusScheduleQueryState(
    val day: MenuItem = MenuItem("Сегодня", "tod"),
    val station: MenuItem = MenuItem("Все станции", "all")
)