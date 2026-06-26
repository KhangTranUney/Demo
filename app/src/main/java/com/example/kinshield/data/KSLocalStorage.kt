package com.example.kinshield.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class KSLocalStorage(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var seenWelcome: Boolean
        get() = prefs.getBoolean(KEY_SEEN_WELCOME, false)
        set(value) = prefs.edit { putBoolean(KEY_SEEN_WELCOME, value) }

    var role: KSRole?
        get() {
            val key = prefs.getString(KEY_ROLE, null) ?: return null
            return KSRole.entries.find { it.storageKey == key }
        }
        set(value) = prefs.edit { putString(KEY_ROLE, value?.storageKey) }

    var completeOnboarding: Boolean
        get() = prefs.getBoolean(KEY_COMPLETE_ONBOARDING, false)
        set(value) = prefs.edit { putBoolean(KEY_COMPLETE_ONBOARDING, value) }

    var familyManagerToken: String?
        get() = prefs.getString(KEY_FAMILY_MANAGER_TOKEN, null)
        set(value) = prefs.edit { putString(KEY_FAMILY_MANAGER_TOKEN, value) }

    companion object {
        private const val PREFS_NAME = "kinshield_prefs"
        private const val KEY_SEEN_WELCOME = "seen_welcome"
        private const val KEY_ROLE = "role"
        private const val KEY_COMPLETE_ONBOARDING = "complete_onboarding"
        private const val KEY_FAMILY_MANAGER_TOKEN = "family_manager_token"
    }
}
