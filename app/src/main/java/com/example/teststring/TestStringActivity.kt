package com.example.teststring

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.demo.ui.BackTopBar
import com.example.ui.theme.DemoTheme
import org.json.JSONArray

class TestStringActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { BackTopBar(title = TestStringActivity::class.java.simpleName) }
                ) { innerPadding ->
                    TestStringScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
private fun TestStringScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val stringKeys = remember(context) { loadTestStringKeys(context) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "${stringKeys.size} strings",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(
            items = stringKeys,
            key = { it }
        ) { key ->
            TestStringRow(stringKey = key)
        }
    }
}


@Composable
private fun TestStringRow(stringKey: String) {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(stringKey, "string", context.packageName)
    val content = if (resourceId == 0) {
        "Missing string resource"
    } else {
        context.resources.getText(resourceId).toString()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringKey,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

private fun loadTestStringKeys(context: Context): List<String> {
    return runCatching {
        context.assets.open(TEST_STRING_KEYS_ASSET).bufferedReader().use { reader ->
            val jsonArray = JSONArray(reader.readText())
            List(jsonArray.length()) { index -> jsonArray.getString(index) }
                .filter { it.isNotBlank() }
        }
    }.getOrDefault(emptyList())
}

private const val TEST_STRING_KEYS_ASSET = "strings_test_keys.json"
