package com.architects.pokearch.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColorScheme(
    primary = primaryDarkColor,
    onPrimary = Color.White,
    background = backgroundDarkColor,
    onBackground = Color.White,
    secondary = secondaryDarkColor,
    onSecondary = Color.Black,
    tertiary = tertiaryDarkColor,
    onTertiary = Color.White,
    inversePrimary = inversePrimaryDarkColor,
    secondaryContainer = inversePrimaryColor,
    onSecondaryContainer = Color.Black
)

private val LightColorPalette = lightColorScheme(
    primary = primaryColor,
    onPrimary = Color.White,
    background = backgroundColor,
    onBackground = Color.Black,
    secondary = secondaryColor,
    onSecondary = Color.Black,
    tertiary = tertiaryColor,
    onTertiary = Color.White,
    inversePrimary = inversePrimaryColor,
    secondaryContainer = inversePrimaryColor,
    onSecondaryContainer = Color.Black
)

@Composable
fun PokeArchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}


@Composable
fun PokeArchScreen(modifier: Modifier = Modifier, content: @Composable () -> Unit){
    PokeArchTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}

@Composable
fun SetStatusBarColor(color: Color = MaterialTheme.colorScheme.background) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = color.toArgb()
        }
    }
}
