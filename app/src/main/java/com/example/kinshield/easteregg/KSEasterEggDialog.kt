package com.example.kinshield.easteregg

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private data class EasterEggAction(
    val id: KSEasterEggActionId,
    val label: String,
)

private val actions = listOf(
    EasterEggAction(
        id = KSEasterEggActionId.FAMILY_MANAGER_CODE_ENTERED,
        label = "Family Manager entered the code",
    ),
)

@Composable
fun KSEasterEggDialog(
    onAction: (id: KSEasterEggActionId) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Easter Egg") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                actions.forEach { action ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .clickable {
                                onAction(action.id)
                                onDismiss()
                            }
                    ) {
                        ListItem(
                            headlineContent = { Text(action.label) },
                            leadingContent = { Icons.Filled.Key }
                        )
                    }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        confirmButton = {}
    )
}
