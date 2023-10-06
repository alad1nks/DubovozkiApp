package com.app.dubovozkiapp.ui.services.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.app.dubovozkiapp.ui.navigation.NavigationItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ServicesScreen(
    navController: NavController
) {
    Scaffold(

    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
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
            Surface(
                onClick = {
                    navController.navigate(NavigationItem.CastellanNavigationScreen.screenRoute)
                }
            ) {
                ListItem(
                    headlineContent = { Text("Кастелянша") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Face,
                            contentDescription = "Localized description",
                        )
                    }
                )
            }
            Divider()
        }
    }
}