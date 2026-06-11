package com.example.demo.sms

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class ComposeSmsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Redirect SENDTO/SEND intents to SmsActivity
        val smsIntent = Intent(this, SmsActivity::class.java).apply {
            data = intent.data
            intent.extras?.let { putExtras(it) }
        }
        startActivity(smsIntent)
        finish()
    }
}
