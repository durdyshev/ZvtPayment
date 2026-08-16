package com.justme.zvtpayment.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = Teal300,
    onPrimary = Navy950,
    primaryContainer = Navy800,
    onPrimaryContainer = Teal300,
    secondary = Mint80,
    onSecondary = Navy950,
    secondaryContainer = Navy800,
    onSecondaryContainer = Mint80,
    tertiary = Amber80,
    onTertiary = Navy950,
    tertiaryContainer = Navy800,
    onTertiaryContainer = Amber80,
    background = Navy950,
    onBackground = SurfaceLight,
    surface = Navy900,
    onSurface = SurfaceLight,
    surfaceVariant = Navy800,
    onSurfaceVariant = SurfaceMuted,
    outline = SurfaceMuted,
    outlineVariant = SurfaceMuted,
    error = Color(0xFFFF6B6B),
    onError = Color.White,
    errorContainer = Color(0xFF3A1012),
    onErrorContainer = Color(0xFFFFC9C9)
)

private val LightColorScheme = lightColorScheme(
    primary = Navy800,
    onPrimary = Color.White,
    primaryContainer = SurfaceMuted,
    onPrimaryContainer = Navy900,
    secondary = Teal500,
    onSecondary = Navy900,
    secondaryContainer = Sky80,
    onSecondaryContainer = Navy900,
    tertiary = Green400,
    onTertiary = Navy900,
    tertiaryContainer = Mint80,
    onTertiaryContainer = Navy900,
    background = SurfaceLight,
    onBackground = Navy900,
    surface = Color.White,
    onSurface = Navy900,
    surfaceVariant = SurfaceMuted,
    onSurfaceVariant = Navy800,
    outline = Navy800.copy(alpha = 0.35f),
    outlineVariant = SurfaceMuted,
    error = Color(0xFFE5484D),
    onError = Color.White,
    errorContainer = Color(0xFFFDE2E4),
    onErrorContainer = Color(0xFF8A1C20)
)

@Composable
fun ZvtPaymentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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