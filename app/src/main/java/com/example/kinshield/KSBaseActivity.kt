package com.example.kinshield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.kinshield.easteregg.KSEasterEggOverlay
import com.example.ui.theme.DemoTheme

abstract class KSBaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                KSEasterEggOverlay(
                    content = { KSContent() },
                    onAction = ::onEasterEggAction
                )
            }
        }
    }

    open fun onEasterEggAction(id: String) {
        // Override in subclasses to handle actions
    }

    @Composable
    abstract fun KSContent()
}
