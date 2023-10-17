package com.app.dubovozkiapp.ui.bus_schedule.screen_state

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.dubovozkiapp.ui.bus_schedule.model.BusScheduleUi

sealed interface BusScheduleScreenState {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Content(pagerState: PagerState, snackbarHostState: SnackbarHostState)

    object Init : BusScheduleScreenState {
        @ExperimentalFoundationApi
        @Composable
        override fun Content(pagerState: PagerState, snackbarHostState: SnackbarHostState) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                HorizontalPager(
                    state = pagerState,
                    pageCount = 2
                ) {
                    Box(modifier = Modifier.fillMaxSize())
                }
                CircularProgressIndicator(
                    modifier = Modifier
                )
            }
        }
    }

    data class Loading(
        val schedule: BusScheduleUi
    ) : BusScheduleScreenState {
        @ExperimentalFoundationApi
        @Composable
        override fun Content(pagerState: PagerState, snackbarHostState: SnackbarHostState) {
            Schedule(pagerState = pagerState, schedule = schedule)
        }
    }

    data class Data(
        val schedule: BusScheduleUi
    ) : BusScheduleScreenState {
        @ExperimentalFoundationApi
        @Composable
        override fun Content(pagerState: PagerState, snackbarHostState: SnackbarHostState) {
            Schedule(pagerState = pagerState, schedule = schedule)
        }
    }

    data class NetworkError(
        val schedule: BusScheduleUi
    ) : BusScheduleScreenState {
        @ExperimentalFoundationApi
        @Composable
        override fun Content(pagerState: PagerState, snackbarHostState: SnackbarHostState) {
            LaunchedEffect(snackbarHostState) {
                val snackbarResult = snackbarHostState.showSnackbar(
                    message = "Проблемы с соединением",
                    actionLabel = "Повторить",
                    withDismissAction = true
                )
                when(snackbarResult) {
                    SnackbarResult.Dismissed -> {

                    }
                    SnackbarResult.ActionPerformed -> {

                    }
                }
            }

            Schedule(pagerState = pagerState, schedule = schedule)
        }
    }

    object DatabaseError : BusScheduleScreenState {
        @ExperimentalFoundationApi
        @Composable
        override fun Content(pagerState: PagerState, snackbarHostState: SnackbarHostState) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                HorizontalPager(
                    state = pagerState,
                    pageCount = 2
                ) {
                    Box(modifier = Modifier.fillMaxSize())
                }
                CircularProgressIndicator(
                    modifier = Modifier
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Schedule(
        pagerState: PagerState,
        schedule: BusScheduleUi
    ) {
        val moscowBusListState = rememberLazyListState(
            initialFirstVisibleItemIndex = schedule.moscow.topItemIndex
        )
        val dubkiBusListState = rememberLazyListState(
            initialFirstVisibleItemIndex = schedule.dubki.topItemIndex
        )
        HorizontalPager(
            state = pagerState,
            pageCount = 2
        ) { page ->
            when(page) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = moscowBusListState
                    ) {
                        items(schedule.moscow.busList) {bus ->
                            bus.Content()
                        }
                    }
                }
                1 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = dubkiBusListState
                    ) {
                        items(schedule.dubki.busList) {bus ->
                            bus.Content()
                        }
                    }
                }
            }
        }
    }
}