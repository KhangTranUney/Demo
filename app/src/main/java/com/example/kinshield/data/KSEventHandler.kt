package com.example.kinshield.data

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object KSEventHandler {
    private val _events = MutableSharedFlow<KSEvent>(extraBufferCapacity = 64)
    val events = _events.asSharedFlow()

    fun emit(event: KSEvent) {
        _events.tryEmit(event)
    }
}
