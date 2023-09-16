package com.app.dubovozkiapp.ui.services.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ServicesScreen() {
    Column {
        Surface(
            onClick = {

            }
        ) {
            ListItem(
                headlineContent = { Text("Быстрые свидания") },
                leadingContent = {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Localized description",
                    )
                }
            )
        }
        Divider()
    }
}