package com.app.dubovozkiapp.consts

import com.app.dubovozkiapp.ui.views.MenuItem

object Constants {
    val daysSpinnerItems = listOf(
        MenuItem("Сегодня", "tod"),
        MenuItem("Завтра", "tom"),
        MenuItem("Будни", "wkd"),
        MenuItem("Суббота", "std"),
        MenuItem("Воскресенье", "snd"),
    )
    val directions = listOf(
        MenuItem("В Москву", "msk"),
        MenuItem("В Дубки", "dbk")
    )
    val stationsSpinnerItems = listOf(
        MenuItem("Все станции", Station.ALL),
        MenuItem("Одинцово", Station.ODINTSOVO),
        MenuItem("Молодежная", Station.MOLODYOZHNAYA),
        MenuItem("Славянский б-р", Station.SLAVYANKA)
    )
}