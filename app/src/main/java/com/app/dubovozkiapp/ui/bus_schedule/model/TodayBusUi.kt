package com.app.dubovozkiapp.ui.bus_schedule.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.dubovozkiapp.consts.Station

data class TodayBusUi(
    val id: Int,
    val time: String,
    val timeLeft: String,
    val timeLeftSoon: Boolean,
    val station: String
) : BusUi() {
    @Composable
    override fun Content() {
        Surface(
            onClick = {

            }
        ) {
            Column {
                ListItem(
                    modifier = Modifier
                        .padding(8.dp),
                    headlineContent = {
                        if (this@TodayBusUi.timeLeftSoon) {
                            Text(
                                text = this@TodayBusUi.timeLeft,
                                modifier = Modifier
                                    .background(color = Color.Red, shape = MaterialTheme.shapes.small)
                                    .padding(vertical = 8.dp, horizontal = 12.dp),
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        } else {
                            Text(
                                text = this@TodayBusUi.timeLeft,
                                fontSize = 12.sp
                            )
                        }
                    },
                    leadingContent = {
                        when (this@TodayBusUi.station) {
                            Station.ODINTSOVO -> {
                                Text(
                                    text = this@TodayBusUi.time,
                                    fontSize = 24.sp
                                )
                            }
                            Station.SLAVYANKA -> {
                                Text(
                                    text = this@TodayBusUi.time,
                                    color = Color.Green,
                                    fontSize = 24.sp
                                )
                            }
                            Station.MOLODYOZHNAYA -> {
                                Text(
                                    text = this@TodayBusUi.time,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    },
                    trailingContent = {
                        when (this@TodayBusUi.station) {
                            Station.ODINTSOVO -> {
                                Text(
                                    text = "Одинцово",
                                    fontSize = 16.sp
                                )
                            }
                            Station.SLAVYANKA -> {
                                Text(
                                    text = "Славянский б-р",
                                    color = Color.Green,
                                    fontSize = 16.sp
                                )
                            }
                            Station.MOLODYOZHNAYA -> {
                                Text(
                                    text = "Молодёжная",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                )
                Divider()
            }
        }
    }
}