package com.app.dubovozkiapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.dubovozkiapp.ui.registration.view.RegistrationScreen
import com.app.dubovozkiapp.ui.settings.viewmodel.SettingsViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationItem.BottomNavigationScreen.screenRoute,
    settingsViewModel: SettingsViewModel
){
    Scaffold(
        content = {
            Box(modifier = Modifier.padding(it)) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = modifier
                ) {
                    composable(NavigationItem.RegistrationScreen.screenRoute) {
                        RegistrationScreen(navController = navController)
                    }
                    composable(NavigationItem.BottomNavigationScreen.screenRoute) {
                        BottomNavGraph(
                            navController = navController,
                            settingsViewModel = settingsViewModel
                        )
                    }
                }
            }
        }
    )
}