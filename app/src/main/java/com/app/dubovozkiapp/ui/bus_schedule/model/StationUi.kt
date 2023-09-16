package com.app.dubovozkiapp.ui.bus_schedule.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

sealed interface StationUi {
    fun timeContent(time: String): @Composable () -> Unit
    fun nameContent(): @Composable () -> Unit

    object Odintsovo : StationUi {
        override fun timeContent(time: String): @Composable () -> Unit = {
            Text(
                text = time,
                fontSize = 24.sp
            )
        }

        override fun nameContent(): @Composable () -> Unit = {
            Text(
                text = "Одинцово",
                fontSize = 16.sp
            )
        }

    }

    object Slavyanka : StationUi {

        override fun timeContent(time: String): @Composable () -> Unit = {
            Text(
                text = time,
                color = Color.Green,
                fontSize = 24.sp
            )
        }

        override fun nameContent(): @Composable () -> Unit = {
            Text(
                text = "Славянский б-р",
                color = Color.Green,
                fontSize = 16.sp
            )
        }

    }

    object Molodyozhnaya : StationUi {
        override fun timeContent(time: String): @Composable () -> Unit = {
            Text(
                text = time,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp
            )
        }

        override fun nameContent(): @Composable () -> Unit = {
            Text(
                text = "Молодёжная",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
        }
    }
}