package com.app.dubovozkiapp.ui.navigation

sealed class NavigationItem(val screenRoute: String) {
    object RegistrationScreen : NavigationItem("registration")
    object BottomNavigationScreen : NavigationItem("bottom")
    object CastellanNavigationScreen : NavigationItem("castellan")
}