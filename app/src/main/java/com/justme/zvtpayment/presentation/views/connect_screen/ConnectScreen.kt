package com.justme.zvtpayment.presentation.views.connect_screen

import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.edit

@Composable
fun ConnectionSettingsView(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("zvt_settings", MODE_PRIVATE)
    val ip = prefs.getString("server_address", "192.168.178.203") ?: "192.168.178.203"
    val port = prefs.getInt("server_port", 20007)
    var ipAddress by remember { mutableStateOf(ip) }
    var portText by remember { mutableStateOf(port.toString()) }


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
                    text = "Connection Settings",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = ipAddress,
                onValueChange = { ipAddress = it },
                label = { Text("Ip Address") },
                placeholder = { Text("192.168.1.100") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = portText,
                onValueChange = { portText = it },
                label = { Text("Port") },
                placeholder = { Text("20007") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val port = portText.toIntOrNull()
                    if (port == null || port !in 1..65535) {
                        Toast.makeText(context, "Please enter a valid port.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (ipAddress.isBlank()) {
                        Toast.makeText(context,"Please enter an IP address.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // Save to SharedPreferences
                    val prefs = context.getSharedPreferences("zvt_settings", MODE_PRIVATE)
                    prefs.edit {
                        putString("server_address", ipAddress)
                            .putInt("server_port", port)
                    }


                    Toast.makeText(context, "Settings is saved!!!", Toast.LENGTH_SHORT).show()

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}