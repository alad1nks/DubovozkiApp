package com.app.dubovozkiapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.dubovozkiapp.data.bus_schedule.entities.BusEntity

@Dao
interface AppDao {
    @Query("SELECT * FROM bus_schedule WHERE direction = 'msk' and day = :day and station = :station")
    suspend fun getMoscowBusList(day: Int, station: String): List<BusEntity>

    @Query("SELECT * FROM bus_schedule WHERE direction = 'dbk' and day = :day and station = :station")
    suspend fun getDubkiBusList(day: Int, station: String): List<BusEntity>

    @Query("SELECT * FROM bus_schedule WHERE direction = 'msk' and day = :day")
    suspend fun getMoscowBusListAllStation(day: Int): List<BusEntity>

    @Query("SELECT * FROM bus_schedule WHERE direction = 'dbk' and day = :day")
    suspend fun getDubkiBusListAllStation(day: Int): List<BusEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun updateSchedule(schedule: List<BusEntity>)

    @Query("DELETE FROM bus_schedule")
    fun clearSchedule()
}