package com.example.familyfinancetracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(

    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,

    background = Color.Black,
    surface = Color.Black,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,

    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(

    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    background = Color.White,
    surface = Color.White,

    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,

    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun FamilyFinanceTrackerTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme)
            DarkColorScheme
        else
            LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}