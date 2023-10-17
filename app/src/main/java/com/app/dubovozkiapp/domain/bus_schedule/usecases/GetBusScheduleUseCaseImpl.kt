package com.app.dubovozkiapp.domain.bus_schedule.usecases

import com.app.dubovozkiapp.consts.DayOfTheWeek
import com.app.dubovozkiapp.consts.ScheduleDay
import com.app.dubovozkiapp.consts.Station
import com.app.dubovozkiapp.data.bus_schedule.entities.BusEntity
import com.app.dubovozkiapp.data.bus_schedule.repositories.BusScheduleRepository
import com.app.dubovozkiapp.domain.bus_schedule.factory.BusScheduleDomainFactory.toDomain
import com.app.dubovozkiapp.domain.bus_schedule.model.BusDomain
import com.app.dubovozkiapp.domain.bus_schedule.model.BusScheduleDomain
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
        val busList = getBusScheduleFromRepository(day, station)
        if (day != ScheduleDay.TODAY) {
            return BusScheduleDomain(
                moscowSchedule = OneDirectionScheduleDomain(busList = busList.first.toDomain()),
                dubkiSchedule = OneDirectionScheduleDomain(busList = busList.second.toDomain())
            )
        }

        val (firstMoscowBus, firstDubkiBus) = busList.toDomain().getFirstBuses()

        return BusScheduleDomain(
            moscowSchedule = OneDirectionScheduleDomain(firstMoscowBus, busList.toDomain().first),
            dubkiSchedule = OneDirectionScheduleDomain(firstDubkiBus, busList.toDomain().second)
        )
    }

    private suspend fun getBusScheduleFromRepository(
        day: String,
        station: String
    ): Pair<List<BusEntity>, List<BusEntity>> {
        return when(station) {
            Station.ALL -> repository.getBusListAllStation(day.query())
            else -> repository.getBusList(day.query(), station)
        }
    }


    private fun Pair<List<BusDomain>, List<BusDomain>>.getFirstBuses(): Array<Int> {
        var firstMoscowBus = 0
        run breaking@ {
            this.first.forEachIndexed { index, busDomain ->
                if (busDomain is BusDomain.Today) {
                    firstMoscowBus = index
                    return@breaking
                }
            }
        }
        var firstDubkiBus = 0
        run breaking@{
            this.second.forEachIndexed { index, busDomain ->
                if (busDomain is BusDomain.Today) {
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
}