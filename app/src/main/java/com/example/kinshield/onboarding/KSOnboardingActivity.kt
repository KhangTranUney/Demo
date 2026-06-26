package com.example.kinshield.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.kinshield.data.KSLocalStorage
import com.example.kinshield.home.KSHomeActivity
import com.example.ui.theme.DemoTheme
import kotlinx.serialization.Serializable
import java.util.UUID

class KSOnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val storage = KSLocalStorage(this)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                val startRoute = remember { if (storage.seenWelcome) RoleSelectionRoute else WelcomeRoute }
                val onFinish: () -> Unit = remember {
                    {
                        val intent = Intent(this@KSOnboardingActivity, KSHomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
                KSOnboardingScreen(
                    storage = storage,
                    startRoute = startRoute,
                    onFinish = onFinish,
                )
            }
        }
    }
}

@Serializable private data object WelcomeRoute : NavKey
@Serializable private data object RoleSelectionRoute : NavKey
@Serializable private data object SignInRoute : NavKey
@Serializable private data object ShareCodeRoute : NavKey

@Composable
private fun KSOnboardingScreen(
    storage: KSLocalStorage,
    startRoute: NavKey,
    onFinish: () -> Unit,
) {
    val backStack = rememberNavBackStack(startRoute)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<WelcomeRoute> {
                KSWelcomeScreen(
                    onEnter = {
                        storage.seenWelcome = true
                        backStack.add(RoleSelectionRoute)
                    }
                )
            }
            entry<RoleSelectionRoute> {
                KSRoleSelectionScreen(
                    onRoleSelected = { role ->
                        storage.role = role
                        when (role) {
                            KSLocalStorage.ROLE_FAMILY_MANAGER -> backStack.add(SignInRoute)
                            KSLocalStorage.ROLE_FAMILY_DEVICE -> backStack.add(ShareCodeRoute)
                        }
                    },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<SignInRoute> {
                KSSignInScreen(
                    onSignIn = {
                        storage.familyManagerToken = UUID.randomUUID().toString()
                        onFinish()
                    },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<ShareCodeRoute> {
                KSShareCodeScreen(
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
