package com.example.demo.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony

class SmsDeliverReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Required for default SMS app. Writes incoming SMS to the provider.
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        for (sms in messages) {
            val values = android.content.ContentValues().apply {
                put("address", sms.displayOriginatingAddress)
                put("body", sms.messageBody)
                put("date", sms.timestampMillis)
                put("read", 0)
                put("seen", 0)
            }
            context.contentResolver.insert(
                android.net.Uri.parse("content://sms/inbox"),
                values
            )
        }
    }
}
