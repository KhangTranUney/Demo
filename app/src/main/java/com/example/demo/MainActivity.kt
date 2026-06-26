package com.example.demo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.demo.customtab.CustomTabActivity
import com.example.demo.snackbar.SnackbarActivity
import com.example.demo.sms.SmsActivity
import com.example.demo.ui.BackTopBar
import com.example.demo.ui.theme.DemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        BackTopBar(
                            title = MainActivity::class.java.simpleName,
                            showBack = false
                        )
                    }
                ) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(name = "Android")
        Button(
            onClick = {
                context.startActivity(Intent(context, SmsActivity::class.java))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Open SMS")
        }
        Button(
            onClick = {
                context.startActivity(Intent(context, CustomTabActivity::class.java))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Open Custom Tab")
        }
        Button(
            onClick = {
                context.startActivity(Intent(context, SnackbarActivity::class.java))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Open Snackbar Demo")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoTheme {
        Greeting("Android")
    }
}