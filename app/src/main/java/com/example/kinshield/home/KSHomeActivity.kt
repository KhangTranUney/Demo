package com.example.kinshield.home

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kinshield.KSBaseActivity
import com.example.kinshield.data.KSLocalStorage
import com.example.kinshield.data.KSRole
import com.example.kinshield.ui.KSTopBar

private data class Tab(val title: String, val icon: ImageVector)

private val tabs = listOf(
    Tab("Home", Icons.Filled.Home),
    Tab("Inbox", Icons.Filled.Inbox),
    Tab("Tools", Icons.Filled.Build),
    Tab("Settings", Icons.Filled.Settings),
)

class KSHomeActivity : KSBaseActivity() {
    private lateinit var storage: KSLocalStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        storage = KSLocalStorage(this)
        storage.completeOnboarding = true
        super.onCreate(savedInstanceState)
    }

    @Composable
    override fun KSContent() {
        var selectedTab by remember { mutableIntStateOf(0) }
        val role = remember { storage.role ?: KSRole.FAMILY_DEVICE }
        val hasDeviceToken = storage.familyDeviceToken != null

        Scaffold(
            topBar = {
                KSTopBar(
                    title = "KinShield",
                    description = when (role) {
                        KSRole.FAMILY_MANAGER -> "Family Manager"
                        KSRole.FAMILY_DEVICE -> "Family Device"
                    },
                    showBack = false
                )
            },
            bottomBar = {
                NavigationBar {
                    tabs.forEachIndexed { index, tab ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            icon = { Icon(tab.icon, contentDescription = tab.title) },
                            label = { Text(tab.title) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            when (selectedTab) {
                1 -> KSInboxScreen(
                    role = role,
                    modifier = Modifier.padding(innerPadding)
                )
                3 -> KSSettingsScreen(
                    role = role,
                    hasDeviceToken = hasDeviceToken,
                    onSignOut = {
                        storage.clearAll()
                        val intent = Intent(this@KSHomeActivity, com.example.kinshield.onboarding.KSOnboardingActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    },
                    onLeave = {
                        storage.clearAll()
                        val intent = Intent(this@KSHomeActivity, com.example.kinshield.onboarding.KSOnboardingActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    },
                    onOpenFamilyManagement = {
                        startActivity(Intent(this@KSHomeActivity, com.example.kinshield.familymanagement.KSFamilyManagementActivity::class.java))
                    },
                    onProtectThisDevice = {
                        startActivity(Intent(this@KSHomeActivity, com.example.kinshield.devicesetup.KSDeviceSetupActivity::class.java))
                    },
                    onControlThisDevice = {
                        val intent = Intent(this@KSHomeActivity, com.example.kinshield.familymanagement.KSFamilyManagementActivity::class.java)
                        intent.putExtra(com.example.kinshield.familymanagement.KSFamilyManagementActivity.EXTRA_SKIP_AUTH, true)
                        startActivity(intent)
                    },
                    modifier = Modifier.padding(innerPadding)
                )
                else -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(tabs[selectedTab].title)
                }
            }
        }
    }
}
