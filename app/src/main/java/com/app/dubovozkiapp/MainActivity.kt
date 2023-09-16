package com.app.dubovozkiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.app.dubovozkiapp.ui.navigation.NavGraph
import com.app.dubovozkiapp.ui.settings.viewmodel.SettingsViewModel
import com.app.dubovozkiapp.ui.theme.DubovozkiAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DubovozkiAppTheme(
                viewModel = viewModel
            ) {
                NavGraph(
                    settingsViewModel = viewModel
                )
            }
        }
    }
}