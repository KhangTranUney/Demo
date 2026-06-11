package com.example.demo.customtab

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.demo.ui.BackTopBar
import com.example.demo.ui.theme.DemoTheme

private const val NETLIFY_DEMO_URL = "https://grand-banoffee-81654c.netlify.app/"
//private const val NETLIFY_DEMO_URL = "https://incomparable-pegasus-c826a7.netlify.app/"

class CustomTabActivity : ComponentActivity() {
    private var deepLinkData by mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        deepLinkData = intent?.takeIf { it.action == Intent.ACTION_VIEW }?.data
        setContent {
            DemoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { BackTopBar(title = CustomTabActivity::class.java.simpleName) }
                ) { innerPadding ->
                    CustomTabScreen(
                        deepLinkData = deepLinkData,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent.action == Intent.ACTION_VIEW) {
            deepLinkData = intent.data
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

@Composable
fun CustomTabScreen(deepLinkData: Uri?, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val intent = CustomTabsIntent.Builder().build()
                intent.launchUrl(context, "https://www.google.com".toUri())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open google.com in Custom Tab")
        }
        Button(
            onClick = {
                val cct = CustomTabsIntent.Builder().build()
                cct.launchUrl(context, NETLIFY_DEMO_URL.toUri())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Open CCT \u2192 Netlify-hosted page")
        }
        Button(
            onClick = {
                // Sanity check: fire the deep link as an intent directly, no CCT.
                val deeplink = Intent(
                    Intent.ACTION_VIEW,
                    "demoapp://callback?token=direct&from=button".toUri()
                )
                context.startActivity(deeplink)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Fire demoapp:// directly (no CCT)")
        }

        if (deepLinkData != null) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))
            Text(
                text = "Received deep link",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = formatDeepLink(deepLinkData), modifier = Modifier.padding(top = 8.dp))
        }
    }
}

private fun formatDeepLink(uri: Uri): String = buildString {
    append("URI: ").append(uri).append('\n')
    append("Scheme: ").append(uri.scheme).append('\n')
    append("Host: ").append(uri.host).append('\n')
    append("Path: ").append(uri.path).append('\n')
    val params = uri.queryParameterNames
    if (params.isNotEmpty()) {
        append("Query:\n")
        params.forEach {
            append("  ").append(it).append(" = ").append(uri.getQueryParameter(it)).append('\n')
        }
    }
}
