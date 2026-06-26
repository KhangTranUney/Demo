package com.example.kinshield.onboarding

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kinshield.data.KSEvent
import com.example.kinshield.data.KSEventHandler
import com.example.kinshield.ui.KSTopBar
import kotlin.random.Random

@Composable
fun KSShareCodeScreen(
    onFinish: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val code = remember { "%06d".format(Random.nextInt(1_000_000)) }

    LaunchedEffect(Unit) {
        KSEventHandler.events.collect { event ->
            if (event is KSEvent.FamilyDeviceCodeEntered) {
                onFinish()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            KSTopBar(title = "Share Code", description = "Family Device", showBack = true, onBack = onBack)
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
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "My KinShield code: $code")
                    }
                    context.startActivity(Intent.createChooser(intent, "Share code"))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            ) {
                Text("Share")
            }
        }
    }
}
