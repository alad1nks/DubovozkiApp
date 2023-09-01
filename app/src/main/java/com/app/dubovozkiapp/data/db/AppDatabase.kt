package com.app.dubovozkiapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.dubovozkiapp.data.bus_schedule.entities.BusEntity

@Database(
    entities = [
        BusEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): AppDao
}