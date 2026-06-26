package com.example.demo.snackbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.demo.ui.BackTopBar
import com.example.ui.theme.DemoTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class SnackbarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                SnackbarScreen()
            }
        }
    }
}

@Serializable private data object Screen1 : NavKey
@Serializable private data object Screen2 : NavKey
@Serializable private data object Screen3 : NavKey

private const val LONG_MESSAGE =
    "This is a long description (over 2 lines), so the button layout will need to be changed to display the content better."

@Composable
private fun SnackbarScreen() {
    val backStack = rememberNavBackStack(Screen1)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun showSnackbar(message: String, layout: SnackbarLayout) {
        scope.launch {
            snackbarHostState.showSnackbar(
                CustomSnackbarVisuals(
                    message = message,
                    actionLabel = "Button",
                    layout = layout
                )
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { BackTopBar(title = SnackbarActivity::class.java.simpleName) }
        ) { innerPadding ->
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                modifier = Modifier.padding(innerPadding),
                entryProvider = entryProvider {
                entry<Screen1> {
                    ScreenContent(
                        title = "Screen 1",
                        onShow = { showSnackbar(LONG_MESSAGE, it) },
                        onNext = { backStack.add(Screen2) },
                        showBack = false,
                        onBack = { backStack.removeLastOrNull() }
                    )
                }
                entry<Screen2> {
                    ScreenContent(
                        title = "Screen 2",
                        onShow = { showSnackbar(LONG_MESSAGE, it) },
                        onNext = { backStack.add(Screen3) },
                        showBack = true,
                        onBack = { backStack.removeLastOrNull() }
                    )
                }
                entry<Screen3> {
                    ScreenContent(
                        title = "Screen 3",
                        onShow = { showSnackbar(LONG_MESSAGE, it) },
                        onNext = null,
                        showBack = true,
                        onBack = { backStack.removeLastOrNull() }
                    )
                }
            }
            )
        }
        // SnackbarHost lives OUTSIDE the Scaffold but still in the activity's composition,
        // overlaid at the bottom of the screen.
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding(),
            snackbar = { data -> CustomSnackbar(data) }
        )
    }
}

@Composable
private fun ScreenContent(
    title: String,
    onShow: (SnackbarLayout) -> Unit,
    onNext: (() -> Unit)?,
    showBack: Boolean,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            Button(
                onClick = { onShow(SnackbarLayout.Horizontal) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text("Show Snackbar (horizontal)")
            }
            Button(
                onClick = { onShow(SnackbarLayout.Vertical) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Show Snackbar (vertical)")
            }
            if (onNext != null) {
                OutlinedButton(
                    onClick = onNext,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("Next")
                }
            }
        }
        if (showBack) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}
