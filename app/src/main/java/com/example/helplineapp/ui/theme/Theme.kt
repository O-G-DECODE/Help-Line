package com.example.helplineapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = TealPrimary,
    secondary = TealDark,
    tertiary = LightGray,
    background = TextPrimary,
    surface = TextPrimary,
    onPrimary = White,
    onBackground = SoftBlue,
    onSurface = SoftBlue
)

private val LightColorScheme = lightColorScheme(
    primary = TealPrimary,
    secondary = TealDark,
    tertiary = LightGray,
    background = SoftBlue,
    surface = White,
    onPrimary = White,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun HelpLineAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We default this to false so our custom premium colors are always shown
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}