package com.app.dubovozkiapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DepartureBoard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material.icons.outlined.DepartureBoard
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Widgets
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItem(val title: String, val icon: ImageVector, val selectedIcon: ImageVector, val screenRoute: String) {
    object BusScheduleScreen : BottomNavigationItem("Расписание", Icons.Outlined.DepartureBoard, Icons.Filled.DepartureBoard,"bus_schedule")
    object ServicesScreen: BottomNavigationItem("Сервисы", Icons.Outlined.Widgets, Icons.Filled.Widgets,"services")
    object SettingsScreen: BottomNavigationItem("Настройки", Icons.Outlined.Settings, Icons.Filled.Settings,"settings")
}