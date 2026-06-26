package com.example.kinshield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.kinshield.data.KSEvent
import com.example.kinshield.data.KSEventHandler
import com.example.kinshield.data.KSLocalStorage
import com.example.kinshield.easteregg.KSEasterEggActionId
import com.example.kinshield.easteregg.KSEasterEggOverlay
import com.example.ui.theme.DemoTheme
import java.util.UUID

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

    private fun onEasterEggAction(id: KSEasterEggActionId) {
        val storage = KSLocalStorage(this)
        when (id) {
            KSEasterEggActionId.FAMILY_MANAGER_CODE_ENTERED -> {
                storage.familyDeviceToken = UUID.randomUUID().toString()
                storage.completeOnboarding = true
                KSEventHandler.emit(KSEvent.FamilyDeviceCodeEntered)
            }
        }
    }

    @Composable
    abstract fun KSContent()
}
