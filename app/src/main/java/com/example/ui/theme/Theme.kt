package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val KaziDarkColorScheme = darkColorScheme(
    primary = KaziGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = KaziGreenDark,
    onPrimaryContainer = Color.White,
    secondary = KaziGoldAccent,
    onSecondary = TanzanianFlagBlack,
    secondaryContainer = KaziGoldDark,
    onSecondaryContainer = Color.White,
    tertiary = KaziBlueAccent,
    onTertiary = Color.White,
    background = SurfaceDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceCardDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = TextSecondaryDark
)

private val KaziLightColorScheme = lightColorScheme(
    primary = KaziGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = KaziGreenLight,
    onPrimaryContainer = KaziGreenDark,
    secondary = KaziGoldAccent,
    onSecondary = TanzanianFlagBlack,
    secondaryContainer = KaziGoldLight,
    onSecondaryContainer = KaziGoldDark,
    tertiary = KaziBlueAccent,
    onTertiary = Color.White,
    background = SurfaceLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceCardLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = TextSecondaryLight
)

@Composable
fun KaziHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Preserve brand identity with Tanzanian colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> KaziDarkColorScheme
        else -> KaziLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Backwards compatibility alias for template references
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    KaziHubTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
