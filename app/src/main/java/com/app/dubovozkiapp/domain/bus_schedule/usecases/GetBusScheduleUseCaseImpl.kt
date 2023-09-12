package com.app.dubovozkiapp.domain.bus_schedule.usecases

import android.util.Log
import com.app.dubovozkiapp.consts.DayOfTheWeek
import com.app.dubovozkiapp.consts.ScheduleDay
import com.app.dubovozkiapp.consts.Station
import com.app.dubovozkiapp.data.bus_schedule.repositories.BusScheduleRepository
import com.app.dubovozkiapp.domain.bus_schedule.factory.BusScheduleDomainFactory.toDomain
import com.app.dubovozkiapp.domain.bus_schedule.model.BusDomain
import com.app.dubovozkiapp.domain.bus_schedule.model.BusScheduleDomain
import com.app.dubovozkiapp.domain.bus_schedule.model.DepartedBusDomain
import com.app.dubovozkiapp.domain.bus_schedule.model.OneDirectionScheduleDomain
import java.util.Calendar
import javax.inject.Inject

class GetBusScheduleUseCaseImpl @Inject constructor(
    private val repository: BusScheduleRepository
) : GetBusScheduleUseCase {

    override suspend fun getBusSchedule(
        day: String,
        station: String
    ): BusScheduleDomain {

        val busList = when(station) {
            Station.ALL -> repository.getBusListAllStation(day.query())
            else -> repository.getBusList(day.query(), station)
        }

        if (day != ScheduleDay.TODAY) {
            return BusScheduleDomain(
                moscowSchedule = OneDirectionScheduleDomain(busList = busList.first.toDomain()),
                dubkiSchedule = OneDirectionScheduleDomain(busList = busList.second.toDomain())
            )
        }

        val (firstMoscowBus, firstDubkiBus) = busList.toDomain().getFirstBuses()

        return BusScheduleDomain(
            moscowSchedule = oneDirectionScheduleFactory(firstMoscowBus, busList.toDomain().first),
            dubkiSchedule = oneDirectionScheduleFactory(firstDubkiBus, busList.toDomain().second)
        )
    }


    private fun Pair<List<BusDomain.Today>, List<BusDomain.Today>>.getFirstBuses(): Array<Int> {
        var firstMoscowBus = 0
        run breaking@ {
            this.first.forEachIndexed { index, busDomain ->
                if (busDomain.timeLeft >= 0) {
                    firstMoscowBus = index
                    return@breaking
                }
            }
        }
        var firstDubkiBus = 0
        run breaking@{
            this.second.forEachIndexed { index, busDomain ->
                if (busDomain.timeLeft >= 0) {
                    firstDubkiBus = index
                    return@breaking
                }
            }
        }
        return arrayOf(firstMoscowBus, firstDubkiBus)
    }

    private fun String.query(): Int {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return when(this) {
            ScheduleDay.TODAY -> when(today) {
                Calendar.MONDAY -> DayOfTheWeek.MONDAY
                Calendar.SATURDAY -> DayOfTheWeek.SATURDAY
                Calendar.SUNDAY -> DayOfTheWeek.SUNDAY
                else -> DayOfTheWeek.WEEKDAY
            }
            ScheduleDay.TOMORROW -> when(today) {
                Calendar.FRIDAY -> DayOfTheWeek.SATURDAY
                Calendar.SATURDAY -> DayOfTheWeek.SUNDAY
                Calendar.SUNDAY -> DayOfTheWeek.MONDAY
                else -> DayOfTheWeek.WEEKDAY
            }
            ScheduleDay.WEEKDAY -> DayOfTheWeek.WEEKDAY
            ScheduleDay.SATURDAY -> DayOfTheWeek.SATURDAY
            else -> DayOfTheWeek.SUNDAY
        }
    }

    private fun oneDirectionScheduleFactory(
        firstBus: Int,
        busList: List<BusDomain.Today>
    ): OneDirectionScheduleDomain = OneDirectionScheduleDomain(
        firstBus = firstBus,
        departedBusList = busList.subList(0, firstBus).toDepartedBusList(),
        busList = busList.subList(firstBus, busList.size)
    )

    private fun List<BusDomain.Today>.toDepartedBusList(): List<DepartedBusDomain> = this.map {
        DepartedBusDomain(
            id = it.id,
            timePassed = -it.timeLeft,
            dayTimeString = it.dayTimeString,
            station = it.station
        )
    }
}