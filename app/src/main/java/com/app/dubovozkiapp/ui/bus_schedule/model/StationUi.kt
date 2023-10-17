package com.app.dubovozkiapp.ui.bus_schedule.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

sealed interface StationUi {
    fun timeContent(time: String): @Composable () -> Unit
    fun nameContent(): @Composable () -> Unit
    fun nameContentDeparted(): @Composable () -> Unit

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

        override fun nameContentDeparted(): @Composable () -> Unit = {
            Text(
                text = "Одинцово",
                color = MaterialTheme.colorScheme.inverseOnSurface,
                fontSize = 16.sp
            )
        }

    }

    object Slavyanka : StationUi {

        override fun timeContent(time: String): @Composable () -> Unit = {
            Text(
                text = time,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 24.sp
            )
        }

        override fun nameContent(): @Composable () -> Unit = {
            Text(
                text = "Славянский б-р",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 16.sp
            )
        }

        override fun nameContentDeparted(): @Composable () -> Unit = {
            Text(
                text = "Славянский б-р",
                color = MaterialTheme.colorScheme.inverseOnSurface,
                fontSize = 16.sp
            )
        }

    }

    object Molodyozhnaya : StationUi {
        override fun timeContent(time: String): @Composable () -> Unit = {
            Text(
                text = time,
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = 24.sp
            )
        }

        override fun nameContent(): @Composable () -> Unit = {
            Text(
                text = "Молодёжная",
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = 16.sp
            )
        }

        override fun nameContentDeparted(): @Composable () -> Unit = {
            Text(
                text = "Молодёжная",
                color = MaterialTheme.colorScheme.inverseOnSurface,
                fontSize = 16.sp
            )
        }
    }
}