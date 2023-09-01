package com.app.dubovozkiapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.app.dubovozkiapp.ui.settings.viewmodel.SettingsViewModel

private val darkColorScheme = darkColorScheme(
    primary = dubkiDarkPrimary,
    onPrimary = dubkiDarkOnPrimary,
    primaryContainer = dubkiDarkPrimaryContainer,
    onPrimaryContainer = dubkiDarkOnPrimaryContainer,
    inversePrimary = dubkiDarkPrimaryInverse,
    secondary = dubkiDarkSecondary,
    onSecondary = dubkiDarkOnSecondary,
    secondaryContainer = dubkiDarkSecondaryContainer,
    onSecondaryContainer = dubkiDarkOnSecondaryContainer,
    tertiary = dubkiDarkTertiary,
    onTertiary = dubkiDarkOnTertiary,
    tertiaryContainer = dubkiDarkTertiaryContainer,
    onTertiaryContainer = dubkiDarkOnTertiaryContainer,
    error = dubkiDarkError,
    onError = dubkiDarkOnError,
    errorContainer = dubkiDarkErrorContainer,
    onErrorContainer = dubkiDarkOnErrorContainer,
    background = dubkiDarkBackground,
    onBackground = dubkiDarkOnBackground,
    surface = dubkiDarkSurface,
    onSurface = dubkiDarkOnSurface,
    inverseSurface = dubkiDarkInverseSurface,
    inverseOnSurface = dubkiDarkInverseOnSurface,
    surfaceVariant = dubkiDarkSurfaceVariant,
    onSurfaceVariant = dubkiDarkOnSurfaceVariant,
    outline = dubkiDarkOutline
)

private val lightColorScheme = lightColorScheme(
    primary = dubkiLightPrimary,
    onPrimary = dubkiLightOnPrimary,
    primaryContainer = dubkiLightPrimaryContainer,
    onPrimaryContainer = dubkiLightOnPrimaryContainer,
    inversePrimary = dubkiLightPrimaryInverse,
    secondary = dubkiLightSecondary,
    onSecondary = dubkiLightOnSecondary,
    secondaryContainer = dubkiLightSecondaryContainer,
    onSecondaryContainer = dubkiLightOnSecondaryContainer,
    tertiary = dubkiLightTertiary,
    onTertiary = dubkiLightOnTertiary,
    tertiaryContainer = dubkiLightTertiaryContainer,
    onTertiaryContainer = dubkiLightOnTertiaryContainer,
    error = dubkiLightError,
    onError = dubkiLightOnError,
    errorContainer = dubkiLightErrorContainer,
    onErrorContainer = dubkiLightOnErrorContainer,
    background = dubkiLightBackground,
    onBackground = dubkiLightOnBackground,
    surface = dubkiLightSurface,
    onSurface = dubkiLightOnSurface,
    inverseSurface = dubkiLightInverseSurface,
    inverseOnSurface = dubkiLightInverseOnSurface,
    surfaceVariant = dubkiLightSurfaceVariant,
    onSurfaceVariant = dubkiLightOnSurfaceVariant,
    outline = dubkiLightOutline
)

@Composable
fun DubovozkiAppTheme(
    viewModel: SettingsViewModel,
    content: @Composable () -> Unit
) {
    val darkTheme by viewModel.darkTheme.observeAsState()
    val dynamicColor by viewModel.dynamicColor.observeAsState()
    val colorScheme = when {
        dynamicColor == true && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme == true) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme == true -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme == true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}