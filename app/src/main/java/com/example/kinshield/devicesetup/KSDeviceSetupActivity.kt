package com.example.kinshield.devicesetup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kinshield.data.KSLocalStorage
import com.example.kinshield.ui.KSTopBar
import com.example.ui.theme.DemoTheme
import kotlin.random.Random
import java.util.UUID

class KSDeviceSetupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                KSDeviceSetupScreen(
                    onComplete = {
                        KSLocalStorage(this).familyDeviceToken = UUID.randomUUID().toString()
                        finish()
                    },
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
private fun KSDeviceSetupScreen(
    onComplete: () -> Unit,
    onBack: () -> Unit
) {
    var step by remember { mutableStateOf(0) }

    when (step) {
        0 -> DeviceSetupCodeScreen(
            onConfirm = { step = 1 },
            onBack = onBack
        )
        1 -> DeviceSetupCompleteScreen(onComplete = onComplete)
    }
}

@Composable
private fun DeviceSetupCodeScreen(onConfirm: () -> Unit, onBack: () -> Unit) {
    val code = remember { "%06d".format(Random.nextInt(1_000_000)) }

    Scaffold(
        topBar = {
            KSTopBar(title = "Device Setup", showBack = true, onBack = onBack)
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
                text = code,
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm")
            }
        }
    }
}

@Composable
private fun DeviceSetupCompleteScreen(onComplete: () -> Unit) {
    Scaffold(
        topBar = {
            KSTopBar(title = "Device Setup", showBack = false)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ready to Protect",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Your device will now be monitored and protected by KinShield. You can now control data of this device from the Settings screen.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = onComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                Text("Complete Setup Device")
            }
        }
    }
}
