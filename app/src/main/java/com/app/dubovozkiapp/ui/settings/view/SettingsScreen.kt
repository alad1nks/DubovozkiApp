package com.app.dubovozkiapp.ui.settings.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.dubovozkiapp.ui.settings.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val darkTheme by viewModel.darkTheme.observeAsState(false)
    val dynamicColor by viewModel.dynamicColor.observeAsState(false)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Настройки",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                text = "Оформление",
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                color = MaterialTheme.colorScheme.primary
            )
            ListItem(
                headlineContent = {
                    Text("Тёмная тема")
                },
                trailingContent = {
                    Switch(
                        checked = darkTheme,
                        onCheckedChange = { viewModel.changeDarkMode() }
                    )
                }
            )
            ListItem(
                headlineContent = {
                    Text("Динамические цвета")
                },
                trailingContent = {
                    Switch(
                        checked = dynamicColor,
                        onCheckedChange = { viewModel.changeDynamicColor() }
                    )
                }
            )
        }
    }
}