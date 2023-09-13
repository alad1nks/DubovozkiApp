package com.app.dubovozkiapp.data.bus_schedule.repositories

import com.app.dubovozkiapp.consts.CoroutineTimeout.FIREBASE_REVISION_TIMEOUT
import com.app.dubovozkiapp.consts.CoroutineTimeout.FIREBASE_SCHEDULE_TIMEOUT
import com.app.dubovozkiapp.consts.FirebaseBusSchedule.BUS_LIST
import com.app.dubovozkiapp.consts.FirebaseBusSchedule.BUS_SCHEDULE
import com.app.dubovozkiapp.consts.FirebaseBusSchedule.REVISION
import com.app.dubovozkiapp.consts.RevisionResponse
import com.app.dubovozkiapp.data.bus_schedule.entities.BusEntity
import com.app.dubovozkiapp.data.bus_schedule.model.BusResponse
import com.app.dubovozkiapp.data.db.AppDatabase
import com.app.dubovozkiapp.ktx.suspendCoroutineWithTimeout
import com.app.dubovozkiapp.user.UserManager
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.resume

class BusScheduleRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val userManager: UserManager,
    private val firebaseDatabase: FirebaseDatabase
) : BusScheduleRepository {

    private val dao = db.dao()

    override suspend fun getBusList(
        day: Int,
        station: String
    ): Pair<List<BusEntity>, List<BusEntity>> = Pair(
        dao.getMoscowBusList(day, station),
        dao.getDubkiBusList(day, station)
    )

    override suspend fun getBusListAllStation(
        day: Int
    ): Pair<List<BusEntity>, List<BusEntity>> = Pair(
        dao.getMoscowBusListAllStation(day),
        dao.getDubkiBusListAllStation(day)
    )

    override suspend fun refreshBusSchedule(): RevisionResponse {
        return when(getRevision()) {
            userManager.getRevision() -> RevisionResponse.Equals
            null -> RevisionResponse.NetworkError
            else -> updateSchedule()
        }
    }


    private suspend fun getRevision(): Int? = suspendCoroutineWithTimeout(FIREBASE_REVISION_TIMEOUT) {
        firebaseDatabase.reference.child(BUS_SCHEDULE).child(REVISION).get().addOnSuccessListener { result ->
            val response = result.getValue(Int::class.java)
            it.resume(response)
        }
    }

    private suspend fun updateSchedule(): RevisionResponse {
        return when(val schedule = getSchedule()) {
            null -> RevisionResponse.NetworkError
            else -> {
                dao.clearSchedule()
                dao.updateSchedule(schedule.toEntity())
                userManager.updateRevision()
                RevisionResponse.NotEquals
            }
        }
    }

    private suspend fun getSchedule(): List<BusResponse>? {
        return suspendCoroutineWithTimeout(FIREBASE_SCHEDULE_TIMEOUT) {
            firebaseDatabase.reference.child(BUS_SCHEDULE).child(BUS_LIST).get().addOnSuccessListener { result ->
                val response: List<BusResponse> = result.children.map { snapShot ->
                    snapShot.getValue(BusResponse::class.java) ?: BusResponse()
                }
                it.resume(response)
            }
        }
    }

    private fun List<BusResponse>.toEntity(): List<BusEntity> {
        return this.map { it.toEntity() }
    }
}