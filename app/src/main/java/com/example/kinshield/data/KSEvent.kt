package com.example.kinshield.data

sealed interface KSEvent {
    data object FamilyDeviceCodeEntered : KSEvent
}
