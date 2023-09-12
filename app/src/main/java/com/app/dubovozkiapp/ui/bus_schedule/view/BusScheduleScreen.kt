package com.app.dubovozkiapp.ui.bus_schedule.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.dubovozkiapp.consts.Constants.daysSpinnerItems
import com.app.dubovozkiapp.consts.Constants.directions
import com.app.dubovozkiapp.consts.Constants.stationsSpinnerItems
import com.app.dubovozkiapp.consts.Station.MOLODYOZHNAYA
import com.app.dubovozkiapp.consts.Station.ODINTSOVO
import com.app.dubovozkiapp.consts.Station.SLAVYANKA
import com.app.dubovozkiapp.ui.bus_schedule.model.BusUi
import com.app.dubovozkiapp.ui.bus_schedule.model.DepartedBusUi
import com.app.dubovozkiapp.ui.bus_schedule.screen_state.BusScheduleScreenState
import com.app.dubovozkiapp.ui.bus_schedule.viewmodel.BusScheduleViewModel
import com.app.dubovozkiapp.ui.views.Spinner
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BusScheduleScreen(
    viewModel: BusScheduleViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsState()
    val queryState by viewModel.queryState.collectAsState()
    val state = rememberCollapsingToolbarScaffoldState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState(initialPage = 0)
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    var stationsExpanded by remember { mutableStateOf(false) }
    var daysExpanded by remember { mutableStateOf(false) }

    CollapsingToolbarScaffold(
        modifier = Modifier,
        state = state,
        scrollStrategy = ScrollStrategy.EnterAlways,
        toolbar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                    ) {
                        Spinner(
                            modifier = Modifier
                                .wrapContentSize(Alignment.TopStart)
                                .weight(1f)
                                .padding(end = 16.dp)
                                .clip(shapes.small),
                            expanded = stationsExpanded,
                            onClick = { stationsExpanded = true },
                            onDismiss = { stationsExpanded = false },
                            selected = queryState.station,
                            items = stationsSpinnerItems,
                            onSelect = {
                                viewModel.updateStation(it)
                                stationsExpanded = false
                            }
                        )
                        Spinner(
                            modifier = Modifier
                                .wrapContentSize(Alignment.TopEnd)
                                .weight(1f)
                                .padding(start = 16.dp)
                                .clip(shapes.small),
                            expanded = daysExpanded,
                            onClick = { daysExpanded = true },
                            onDismiss = { daysExpanded = false },
                            selected = queryState.day,
                            items = daysSpinnerItems,
                            onSelect = {
                                viewModel.updateDay(it)
                                daysExpanded = false
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                scrollBehavior = scrollBehavior
            )
        },
    ) {
        Column(modifier = Modifier) {
            TabRow(selectedTabIndex = tabIndex) {
                directions.forEachIndexed { index, direction ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = direction.title, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                    )
                }
            }
            BusSchedule(
                pagerState = pagerState,
                screenState = screenState
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BusSchedule(
    pagerState: PagerState,
    screenState: BusScheduleScreenState
) {
    when(screenState) {
        BusScheduleScreenState.Init -> BusScheduleInit(pagerState)
        is BusScheduleScreenState.Data ->  BusScheduleData(pagerState, screenState)
        else -> BusScheduleInit(pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BusScheduleInit(
    pagerState: PagerState
) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BusScheduleData(
    pagerState: PagerState,
    screenState: BusScheduleScreenState.Data
) {
    val moscowBusListState = rememberLazyListState(
        initialFirstVisibleItemIndex = screenState.schedule.moscow.topItemIndex
    )
    val dubkiBusListState = rememberLazyListState(
        initialFirstVisibleItemIndex = screenState.schedule.dubki.topItemIndex
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
                    screenState.schedule.moscow.departedBusList.forEach {
                        item {
                            DepartedBusItem(item = it)
                        }
                    }
                    screenState.schedule.moscow.busList.forEach {
                        item {
                            it.Content()
                        }
                    }
                }
            }
            1 -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = dubkiBusListState
                ) {
                    screenState.schedule.dubki.departedBusList.forEach {
                        item {
                            DepartedBusItem(item = it)
                        }
                    }
                    screenState.schedule.dubki.busList.forEach {
                        item {
                            it.Content()
                        }
                    }
                }
            }
        }

    }
}



@Composable
private fun DepartedBusItem(
    item: DepartedBusUi
) {
    Surface(
        onClick = {

        }
    ) {
        Column {
            ListItem(
                modifier = Modifier
                    .padding(8.dp),
                headlineContent = {
                    Text(
                        text = item.timeLeft,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontSize = 12.sp
                    )
                },
                leadingContent = {
                    Text(
                        text = item.time,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontSize = 24.sp
                    )
                },
                trailingContent = {
                    Text(
                        text = "Молодёжная",
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontSize = 16.sp
                    )
                }
            )
            Divider()
        }
    }
}
