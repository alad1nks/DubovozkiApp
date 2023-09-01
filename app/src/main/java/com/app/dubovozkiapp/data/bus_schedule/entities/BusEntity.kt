package com.app.dubovozkiapp.data.bus_schedule.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_schedule")
data class BusEntity(
    @PrimaryKey
    val id: Int? = null,
    val day: Int? = null,
    val dayTime: Long? = null,
    val dayTimeString: String? = null,
    val direction: String? = null,
    val station: String? = null
)