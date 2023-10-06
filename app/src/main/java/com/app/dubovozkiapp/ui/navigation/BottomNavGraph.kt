package com.app.dubovozkiapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.dubovozkiapp.ui.bus_schedule.view.BusScheduleScreen
import com.app.dubovozkiapp.ui.castellan.view.CastellanScreen
import com.app.dubovozkiapp.ui.services.view.ServicesScreen
import com.app.dubovozkiapp.ui.settings.view.SettingsScreen
import com.app.dubovozkiapp.ui.settings.viewmodel.SettingsViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter",
    "CoroutineCreationDuringComposition"
)
@Composable
fun BottomNavGraph(
    bottomNavController: NavHostController = rememberNavController(),
    startDestination: String = BottomNavigationItem.BusScheduleScreen.screenRoute,
    navController: NavController,
    settingsViewModel: SettingsViewModel
) {
    val items = listOf(
        BottomNavigationItem.BusScheduleScreen,
        BottomNavigationItem.ServicesScreen,
        BottomNavigationItem.SettingsScreen
    )
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            NavigationBar{
                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            if (currentRoute == item.screenRoute) {
                                Icon(item.selectedIcon, contentDescription = item.title)
                            } else {
                                Icon(item.icon, contentDescription = item.title)
                            }
                        },
                        label = { Text(item.title) },
                        selected = currentRoute == item.screenRoute,
                        onClick = {
                            bottomNavController.navigate(item.screenRoute) {
                                bottomNavController.graph.startDestinationRoute?.let { screenRoute ->
                                    popUpTo(screenRoute) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
        content = { innerPadding ->
            NavHost(
                navController = bottomNavController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(BottomNavigationItem.BusScheduleScreen.screenRoute) {
                    BusScheduleScreen(
                        snackbarHostState = snackbarHostState
                    )
                }
                composable(BottomNavigationItem.ServicesScreen.screenRoute) {
                    ServicesScreen(
                        navController = bottomNavController
                    )
                }
                composable(BottomNavigationItem.SettingsScreen.screenRoute) {
                    SettingsScreen(
                        viewModel = settingsViewModel
                    )
                }

                composable(NavigationItem.CastellanNavigationScreen.screenRoute) {
                    CastellanScreen()
                }
            }
        }
    )
}