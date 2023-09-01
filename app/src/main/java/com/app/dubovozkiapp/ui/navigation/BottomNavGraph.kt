package com.app.dubovozkiapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.dubovozkiapp.ui.bus_schedule.view.BusScheduleScreen
import com.app.dubovozkiapp.ui.services.view.ServicesScreen
import com.app.dubovozkiapp.ui.settings.view.SettingsScreen
import com.app.dubovozkiapp.ui.settings.viewmodel.SettingsViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavGraph(
    modifier: Modifier = Modifier,
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
    Scaffold(
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
                                bottomNavController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
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
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(
                    navController = bottomNavController,
                    startDestination = startDestination,
                    modifier = modifier
                ) {
                    composable(BottomNavigationItem.BusScheduleScreen.screenRoute) {
                        BusScheduleScreen()
                    }
                    composable(BottomNavigationItem.ServicesScreen.screenRoute) {
                        ServicesScreen()
                    }
                    composable(BottomNavigationItem.SettingsScreen.screenRoute) {
                        SettingsScreen(
                            viewModel = settingsViewModel
                        )
                    }
                }
            }
        }
    )
}