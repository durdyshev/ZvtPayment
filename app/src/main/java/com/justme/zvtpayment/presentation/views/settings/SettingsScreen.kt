package com.justme.zvtpayment.presentation.views.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.SettingsEthernet
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.justme.zvtpayment.presentation.views.connect_screen.ConnectionSettingsView

enum class SettingsView {
    Menu, Connection
}

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean = false,
    onNavigateBack: () -> Unit,
    onToggleTheme: (Boolean) -> Unit = {}
) {
    var currentView by remember { mutableStateOf(SettingsView.Menu) }

    when (currentView) {
        SettingsView.Menu -> {
            SettingsMenu(
                isDark = isDarkTheme,
                onNavigateBack = onNavigateBack,
                onNavigateToConnection = { currentView = SettingsView.Connection },
                onToggleTheme = onToggleTheme
            )
        }
        SettingsView.Connection -> {
            ConnectionSettingsView(
                onNavigateBack = { currentView = SettingsView.Menu }
            )
        }
    }
}

@Composable
fun SettingsMenu(
    isDark: Boolean,
    onNavigateBack: () -> Unit,
    onNavigateToConnection: () -> Unit,
    onToggleTheme: (Boolean) -> Unit = {}
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ListItem(
                headlineContent = { Text("ZVT Connection Settings") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.SettingsEthernet, 
                        contentDescription = "Ethernet Settings"
                    ) 
                },
                trailingContent = { 
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Navigate"
                    ) 
                },
                modifier = Modifier.clickable { onNavigateToConnection() }
            )
            ListItem(
                headlineContent = { Text("Dark Theme") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.DarkMode,
                        contentDescription = "Dark Theme"
                    )
                },
                trailingContent = {
                    Switch(
                        checked = isDark,
                        onCheckedChange = {
                            onToggleTheme(it)
                        },
                        colors = SwitchDefaults.colors()
                    )
                },
                modifier = Modifier.clickable {
                    onToggleTheme(!isDark)
                }
            )
        }
    }
}


