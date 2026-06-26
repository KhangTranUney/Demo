package com.example.kinshield.familymanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import com.example.kinshield.ui.KSTopBar
import com.example.ui.theme.DemoTheme

class KSFamilyManagementActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                KSFamilyManagementScreen(onClose = { finish() })
            }
        }
    }
}

@Composable
private fun KSFamilyManagementScreen(onClose: () -> Unit) {
    var authenticated by remember { mutableStateOf(false) }

    if (authenticated) {
        FamilyManagementContent(onClose = onClose)
    } else {
        CodeEntryScreen(
            onAuthenticated = { authenticated = true },
            onClose = onClose
        )
    }
}

@Composable
private fun CodeEntryScreen(
    onAuthenticated: () -> Unit,
    onClose: () -> Unit
) {
    var code by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            KSTopBar(title = "Family Management", showBack = true, onBack = onClose)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter Magic Code",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter the 6-digit code to access family management features.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = code,
                onValueChange = {
                    code = it.take(6)
                    error = false
                },
                label = { Text("Code") },
                singleLine = true,
                isError = error,
                supportingText = if (error) {{ Text("Invalid code") }} else null,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (code == "000000") {
                        onAuthenticated()
                    } else {
                        error = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}

@Composable
private fun FamilyManagementContent(onClose: () -> Unit) {
    Scaffold(
        topBar = {
            KSTopBar(title = "Family Management", showBack = true, onBack = onClose)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ManagementSectionCard("Modify Devices", "View and manage paired devices") { }
            ManagementSectionCard("Call Block List", "Manage blocked and allowed phone numbers") { }
            ManagementSectionCard("Safe Websites", "Manage allowed and blocked websites") { }
            ManagementSectionCard("Screen Time Limits", "Set daily screen time restrictions") { }
            ManagementSectionCard("App Restrictions", "Manage allowed and blocked apps") { }
        }
    }
}

@Composable
private fun ManagementSectionCard(title: String, description: String, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleSmall)
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
