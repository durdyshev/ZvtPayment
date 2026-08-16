package com.justme.zvtpayment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.justme.zvtpayment.presentation.views.main_screen.MainScreen
import com.justme.zvtpayment.ui.theme.ZvtPaymentTheme
import androidx.core.content.edit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val systemTheme = isSystemInDarkTheme()
            val themePrefs = remember { getSharedPreferences("zvt_settings", MODE_PRIVATE) }
            var isDarkTheme by remember {
                mutableStateOf(
                    if (themePrefs.contains("is_dark_theme")) {
                        themePrefs.getBoolean("is_dark_theme", false)
                    } else {
                        systemTheme
                    }
                )
            }

            ZvtPaymentTheme(darkTheme = isDarkTheme) {
                MainScreen(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { dark ->
                        isDarkTheme = dark
                        themePrefs.edit { putBoolean("is_dark_theme", dark) }
                    }
                )
            }
        }
    }
}
