package com.app.dubovozkiapp.ui.bus_schedule.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dubovozkiapp.consts.CoroutineTimeout.REFRESH_DELAY
import com.app.dubovozkiapp.domain.bus_schedule.usecases.GetBusScheduleUseCase
import com.app.dubovozkiapp.domain.bus_schedule.usecases.RefreshBusScheduleUseCase
import com.app.dubovozkiapp.ktx.runCatchingNonCancellation
import com.app.dubovozkiapp.ui.bus_schedule.factory.BusScheduleUiFactory.toUi
import com.app.dubovozkiapp.ui.bus_schedule.model.BusScheduleUi
import com.app.dubovozkiapp.ui.bus_schedule.screen_state.BusScheduleQueryState
import com.app.dubovozkiapp.ui.bus_schedule.screen_state.BusScheduleScreenState
import com.app.dubovozkiapp.ui.views.MenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusScheduleViewModel @Inject constructor(
    private val getBusScheduleUseCase: GetBusScheduleUseCase,
    private val refreshBusScheduleUseCase: RefreshBusScheduleUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<BusScheduleScreenState> =
        MutableStateFlow(BusScheduleScreenState.Init)
    val screenState: StateFlow<BusScheduleScreenState> get() = _screenState.asStateFlow()
    private val _queryState: MutableStateFlow<BusScheduleQueryState> = MutableStateFlow(BusScheduleQueryState())
    val queryState: MutableStateFlow<BusScheduleQueryState> get() = _queryState

    private val handler = Handler(Looper.getMainLooper())
    private val updateTask = object : Runnable {
        override fun run() {
            handler.postDelayed(this, REFRESH_DELAY)
            viewModelScope.launch(Dispatchers.IO) {
                refreshBusScheduleScreenState(_queryState.value)
            }
        }
    }

    init {
        handler.post(updateTask)
        subscribeToQueryChanges()
    }


    @OptIn(FlowPreview::class)
    private fun subscribeToQueryChanges() {
        _queryState
            .flatMapConcat { flow { emit(getBusScheduleScreenState(it)) } }
            .onEach {
                _screenState.emit(it)
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private suspend fun getBusScheduleScreenState(
        query: BusScheduleQueryState
    ): BusScheduleScreenState {
        val result = getBusSchedule(query)
        _screenState.emit(result?.let { BusScheduleScreenState.Loading(it) }
            ?: BusScheduleScreenState.DatabaseError)
        val revisionResponse = runCatchingNonCancellation {
            refreshBusScheduleUseCase.refreshBusSchedule()
        }.getOrNull()
        return revisionResponse?.getScreenState(getBusSchedule(query))
            ?: BusScheduleScreenState.DatabaseError
    }

    private suspend fun refreshBusScheduleScreenState(
        query: BusScheduleQueryState
    ) {
        _screenState.emit(getBusSchedule(query)?.let { BusScheduleScreenState.Data(it) }
            ?: BusScheduleScreenState.DatabaseError)
    }

    private suspend fun getBusSchedule(
        query: BusScheduleQueryState
    ): BusScheduleUi? = runCatchingNonCancellation {
        getBusScheduleUseCase.getBusSchedule(
            day = query.day.route,
            station = query.station.route
        ).toUi()
    }.getOrNull()

    fun updateDay(day: MenuItem) {
        _queryState.update {
            it.copy(day = day)
        }
    }

    fun updateStation(station: MenuItem) {
        _queryState.update {
            it.copy(station = station)
        }
    }
}