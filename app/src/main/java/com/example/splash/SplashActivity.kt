package com.example.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.kinshield.data.KSLocalStorage
import com.example.kinshield.data.KSRole
import com.example.kinshield.home.KSHomeActivity
import com.example.kinshield.onboarding.KSOnboardingActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var isReady = false
        splashScreen.setKeepOnScreenCondition { !isReady }

        Handler(Looper.getMainLooper()).postDelayed({
            isReady = true
            val storage = KSLocalStorage(this)
            val target = if (shouldSkipOnboarding(storage)) {
                KSHomeActivity::class.java
            } else {
                KSOnboardingActivity::class.java
            }
            startActivity(Intent(this, target))
            finish()
        }, 250L)
    }

    private fun shouldSkipOnboarding(storage: KSLocalStorage): Boolean {
        val role = storage.role ?: return false
        if (!storage.completeOnboarding) return false
        return when (role) {
            KSRole.FAMILY_MANAGER -> storage.familyManagerToken != null
            KSRole.FAMILY_DEVICE -> storage.familyDeviceToken != null
        }
    }
}
