package com.app.dubovozkiapp.ui.bus_schedule.model

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DepartedBusUi(
    val id: Int,
    val time: String,
    val timeLeft: String,
    val station: String
) {
    @Composable
    fun Content() {
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
                            text = timeLeft,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            fontSize = 12.sp
                        )
                    },
                    leadingContent = {
                        Text(
                            text = time,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            fontSize = 24.sp
                        )
                    },
                    trailingContent = {
                        Text(
                            text = station,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            fontSize = 16.sp
                        )
                    }
                )
                Divider()
            }
        }
    }
}