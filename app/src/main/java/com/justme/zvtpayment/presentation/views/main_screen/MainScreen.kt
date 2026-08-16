package com.justme.zvtpayment.presentation.views.main_screen

import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.justme.zvtlib.ZVTClient
import com.justme.zvtpayment.presentation.components.CircleButton
import com.justme.zvtpayment.presentation.components.NumpadText
import com.justme.zvtpayment.presentation.components.PaymentButton
import com.justme.zvtpayment.presentation.views.settings.SettingsScreen
import kotlinx.coroutines.launch

enum class Screen {
    Main, Settings
}

@Composable
fun MainScreen(
    isDarkTheme: Boolean = false,
    onToggleTheme: (Boolean) -> Unit = {}
) {
    var currentScreen by remember { mutableStateOf(Screen.Main) }
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("zvt_settings", MODE_PRIVATE)
    val ip = prefs.getString("server_address", "192.168.178.203") ?: "192.168.178.203"
    val port = prefs.getInt("server_port", 20007)
    val zvt = remember(ip, port) { ZVTClient(host = ip, port = port) }

    when (currentScreen) {
        Screen.Main -> {
            MainContent(
                zvt = zvt,
                onNavigateToSettings = { currentScreen = Screen.Settings }
            )
        }

        Screen.Settings -> {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onNavigateBack = { currentScreen = Screen.Main },
                onToggleTheme = onToggleTheme
            )
        }
    }
}

@Composable
fun MainContent(
    zvt: ZVTClient,
    onNavigateToSettings: () -> Unit
) {
    val numpadValue = remember { mutableStateOf("0") }
    var isPaymentInProgress by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
            MaterialTheme.colorScheme.background
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                horizontalArrangement = Arrangement.End

            ) {
                IconButton(onClick = onNavigateToSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "Settings"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                NumpadText(numpadValue)

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                    tonalElevation = 8.dp,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        val keypadRows = listOf(
                            listOf("1", "2", "3"),
                            listOf("4", "5", "6"),
                            listOf("7", "8", "9"),
                            listOf("10", "11", "12")
                        )

                        keypadRows.forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                rowItems.forEach { key ->
                                    CircleButton(number = key) { pressedKey ->
                                        numpadValue.value = updateAmount(
                                            currentValue = numpadValue.value,
                                            key = pressedKey
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                PaymentButton(
                    textString = "Payment",
                    enabled = !isPaymentInProgress
                ) {
                    val amount = numpadValue.value.toDoubleOrNull()
                    if (amount == null || amount <= 0.0 || isPaymentInProgress) {
                        scope.launch {
                            snackBarHostState.showSnackbar("Enter a valid amount.")
                        }
                        return@PaymentButton
                    }
                    scope.launch {
                        isPaymentInProgress = true
                        try {
                            if (zvt.isConnected() == null || !zvt.isConnected()!!) {
                                zvt.connect()
                            }
                            val response = zvt.purchase(amount)
                            println("ZVTFunctions1: $response")
                            snackBarHostState.showSnackbar("Payment: $response")
                        } catch (e: Exception) {
                            snackBarHostState.showSnackbar(
                                "Payment Error: ${e.message ?: "Unknown error"}"
                            )
                        } finally {
                            isPaymentInProgress = false
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
            }

            if (isPaymentInProgress) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.45f))
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = "Payment in progress...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

private fun updateAmount(currentValue: String, key: String): String {
    val current = currentValue.ifBlank { "0" }

    return when (key) {
        "12" -> {
            val trimmed = current.dropLast(1)
            when {
                trimmed.isBlank() -> "0"
                trimmed.endsWith(".") -> trimmed.dropLast(1).ifBlank { "0" }
                else -> trimmed
            }
        }

        "10" -> if (current.contains(".")) current else "$current."
        "11" -> if (current == "0") "0" else "${current}0"
        else -> {
            val fraction = current.substringAfter(".", "")
            if (current.contains(".") && fraction.length >= 2) {
                current
            } else if (current == "0") {
                key
            } else {
                "$current$key"
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MainScreen()
}