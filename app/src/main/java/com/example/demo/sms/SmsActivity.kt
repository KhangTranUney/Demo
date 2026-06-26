package com.example.demo.sms

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.app.role.RoleManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.demo.ui.BackTopBar
import com.example.ui.theme.DemoTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SmsMessageItem(
    val id: Long = -1,
    val sender: String,
    val body: String,
    val timestamp: Long
)

class SmsActivity : ComponentActivity() {

    private var phoneNumber by mutableStateOf("")
    private var messageText by mutableStateOf("")
    private var incomingMessages by mutableStateOf(listOf<SmsMessageItem>())
    private var permissionsGranted by mutableStateOf(false)
    private var isDefaultSmsApp by mutableStateOf(false)

    private val smsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
                val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                for (smsMessage: SmsMessage in messages) {
                    val newMessage = SmsMessageItem(
                        sender = smsMessage.displayOriginatingAddress ?: "Unknown",
                        body = smsMessage.messageBody ?: "",
                        timestamp = smsMessage.timestampMillis
                    )
                    incomingMessages = listOf(newMessage) + incomingMessages
                }
            }
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionsGranted = permissions.values.all { it }
        if (permissionsGranted) {
            loadInboxMessages()
        } else {
            Toast.makeText(this, "SMS permissions are required", Toast.LENGTH_SHORT).show()
        }
    }

    private val defaultSmsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d(TAG, "defaultSmsLauncher result: resultCode=${result.resultCode}, data=${result.data}")
        updateDefaultSmsStatus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkAndRequestPermissions()
        updateDefaultSmsStatus()

        setContent {
            DemoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { BackTopBar(title = SmsActivity::class.java.simpleName) }
                ) { innerPadding ->
                    SmsScreen(
                        phoneNumber = phoneNumber,
                        onPhoneNumberChange = { phoneNumber = it },
                        messageText = messageText,
                        onMessageTextChange = { messageText = it },
                        onSendClick = { sendSms() },
                        messages = incomingMessages,
                        permissionsGranted = permissionsGranted,
                        isDefaultSmsApp = isDefaultSmsApp,
                        onRequestPermissions = { checkAndRequestPermissions() },
                        onRequestDefaultSms = { requestDefaultSmsApp() },
                        onDeleteMessage = { message -> deleteSms(message) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateDefaultSmsStatus()
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(smsReceiver, filter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(smsReceiver, filter)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsReceiver)
    }

    private fun checkAndRequestPermissions() {
        val requiredPermissions = arrayOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,
            "android.permission.WRITE_SMS"
        )
        val allGranted = requiredPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
        if (allGranted) {
            permissionsGranted = true
            loadInboxMessages()
        } else {
            permissionLauncher.launch(requiredPermissions)
        }
    }

    private fun loadInboxMessages() {
        val messages = mutableListOf<SmsMessageItem>()
        val cursor = contentResolver.query(
            Uri.parse("content://sms/inbox"),
            arrayOf("_id", "address", "body", "date"),
            null,
            null,
            "date DESC"
        )
        cursor?.use {
            val idIdx = it.getColumnIndexOrThrow("_id")
            val addressIdx = it.getColumnIndexOrThrow("address")
            val bodyIdx = it.getColumnIndexOrThrow("body")
            val dateIdx = it.getColumnIndexOrThrow("date")
            val limit = 50
            var count = 0
            while (it.moveToNext() && count < limit) {
                messages.add(
                    SmsMessageItem(
                        id = it.getLong(idIdx),
                        sender = it.getString(addressIdx) ?: "Unknown",
                        body = it.getString(bodyIdx) ?: "",
                        timestamp = it.getLong(dateIdx)
                    )
                )
                count++
            }
        }
        incomingMessages = messages
    }

    private fun sendSms() {
        if (phoneNumber.isBlank() || messageText.isBlank()) {
            Toast.makeText(this, "Please enter phone number and message", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val smsManager = getSystemService(SmsManager::class.java)
            smsManager.sendTextMessage(phoneNumber, null, messageText, null, null)
            Toast.makeText(this, "SMS sent", Toast.LENGTH_SHORT).show()
            messageText = ""
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send SMS: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteSms(message: SmsMessageItem) {
        try {
            val rows = contentResolver.delete(
                Uri.parse("content://sms/${message.id}"),
                null,
                null
            )
            if (rows > 0) {
                incomingMessages = incomingMessages.filter { it.id != message.id }
                Toast.makeText(this, "SMS deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to delete (requires default SMS app)", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to delete: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateDefaultSmsStatus() {
        val defaultPkg = Telephony.Sms.getDefaultSmsPackage(this)
        isDefaultSmsApp = packageName == defaultPkg
        Log.d(TAG, "updateDefaultSmsStatus: currentPkg=$packageName, defaultSmsPkg=$defaultPkg, isDefault=$isDefaultSmsApp")
    }

    private fun requestDefaultSmsApp() {
        Log.d(TAG, "requestDefaultSmsApp: requesting to become default SMS app")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(RoleManager::class.java)
            val isAvailable = roleManager.isRoleAvailable(RoleManager.ROLE_SMS)
            val isHeld = roleManager.isRoleHeld(RoleManager.ROLE_SMS)
            Log.d(TAG, "requestDefaultSmsApp: RoleManager ROLE_SMS available=$isAvailable, held=$isHeld")

            if (!isAvailable) {
                Log.w(TAG, "requestDefaultSmsApp: ROLE_SMS not available on this device")
                Toast.makeText(this, "SMS role not available on this device", Toast.LENGTH_LONG).show()
                return
            }
            if (isHeld) {
                Log.d(TAG, "requestDefaultSmsApp: already the default SMS app")
                Toast.makeText(this, "Already the default SMS app", Toast.LENGTH_SHORT).show()
                return
            }
            try {
                val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS)
                Log.d(TAG, "requestDefaultSmsApp: launching RoleManager intent")
                defaultSmsLauncher.launch(intent)
                Log.d(TAG, "requestDefaultSmsApp: RoleManager intent launched successfully")
            } catch (e: Exception) {
                Log.e(TAG, "requestDefaultSmsApp: RoleManager failed", e)
                Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT).apply {
                putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
            }
            Log.d(TAG, "requestDefaultSmsApp: intent action=${intent.action}, extra package=$packageName")
            Log.d(TAG, "requestDefaultSmsApp: resolveActivity=${intent.resolveActivity(packageManager)}")
            try {
                defaultSmsLauncher.launch(intent)
                Log.d(TAG, "requestDefaultSmsApp: intent launched successfully")
            } catch (e: Exception) {
                Log.e(TAG, "requestDefaultSmsApp: failed to launch intent", e)
                Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val TAG = "SmsActivity"
    }
}

@Composable
fun SmsScreen(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    messageText: String,
    onMessageTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    messages: List<SmsMessageItem>,
    permissionsGranted: Boolean,
    isDefaultSmsApp: Boolean,
    onRequestPermissions: () -> Unit,
    onRequestDefaultSms: () -> Unit,
    onDeleteMessage: (SmsMessageItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "SMS",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!permissionsGranted) {
            Button(onClick = onRequestPermissions, modifier = Modifier.fillMaxWidth()) {
                Text("Grant SMS Permissions")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (!isDefaultSmsApp) {
            Button(onClick = onRequestDefaultSms, modifier = Modifier.fillMaxWidth()) {
                Text("Set as Default SMS App")
            }
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            Text(
                text = "This app is the default SMS handler",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = { Text("Phone number") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = messageText,
            onValueChange = onMessageTextChange,
            label = { Text("Message") },
            minLines = 3,
            maxLines = 5,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onSendClick,
            enabled = permissionsGranted,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send SMS")
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Incoming Messages",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${messages.size} messages",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (messages.isEmpty()) {
            Text(
                text = if (permissionsGranted) "No messages yet" else "Grant permissions to view messages",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(messages, key = { "${it.id}_${it.sender}_${it.timestamp}" }) { message ->
                    SmsMessageCard(message = message, onDelete = { onDeleteMessage(message) })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun SmsMessageCard(message: SmsMessageItem, onDelete: () -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete SMS") },
            text = { Text("Delete this message from ${message.sender}?") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    onDelete()
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = message.sender,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = dateFormat.format(Date(message.timestamp)),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.body,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_delete),
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
