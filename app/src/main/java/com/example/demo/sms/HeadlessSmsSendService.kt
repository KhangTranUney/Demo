package com.example.demo.sms

import android.app.Service
import android.content.Intent
import android.os.IBinder

class HeadlessSmsSendService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Stub: required for default SMS app eligibility
        stopSelf()
        return START_NOT_STICKY
    }
}
