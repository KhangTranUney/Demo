package com.example.kinshield.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kinshield.data.KSRole

data class InboxItem(
    val title: String,
    val description: String,
)

private data class DeviceGroup(
    val label: String,
    val items: List<InboxItem>,
)

@Composable
fun KSInboxScreen(
    role: KSRole,
    modifier: Modifier = Modifier
) {
    val groups = remember(role) {
        when (role) {
            KSRole.FAMILY_DEVICE -> listOf(
                DeviceGroup(
                    label = "This Device",
                    items = listOf(
                        InboxItem("There is a scam call", "From number +1-555-0147, at 2:35 PM"),
                        InboxItem("Suspicious SMS detected", "Message from unknown sender asking for personal info, at 11:20 AM"),
                        InboxItem("App blocked", "Unknown app tried to access your camera, at 9:05 AM"),
                    )
                )
            )
            KSRole.FAMILY_MANAGER -> listOf(
                DeviceGroup(
                    label = "This Device",
                    items = listOf(
                        InboxItem("There is a scam call", "From number +1-555-0147, at 2:35 PM"),
                        InboxItem("Suspicious SMS detected", "Message from unknown sender, at 11:20 AM"),
                    )
                ),
                DeviceGroup(
                    label = "Device 1",
                    items = listOf(
                        InboxItem("Screen time exceeded", "Device 1 used 3 hours of screen time today, at 6:00 PM"),
                        InboxItem("App installed", "Game 'MineCraft' was installed on Device 1, at 4:15 PM"),
                    )
                ),
                DeviceGroup(
                    label = "Device 2",
                    items = listOf(
                        InboxItem("Web access blocked", "Adult content site was blocked on Device 2, at 8:30 PM"),
                        InboxItem("Location alert", "Device 2 left the safe zone at 3:00 PM"),
                    )
                ),
            )
        }
    }

    var selectedGroupIndex by remember { mutableIntStateOf(0) }
    val currentItems = groups.getOrNull(selectedGroupIndex)?.items.orEmpty()

    Column(modifier = modifier.fillMaxSize()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(groups.size) { index ->
                FilterChip(
                    selected = selectedGroupIndex == index,
                    onClick = { selectedGroupIndex = index },
                    label = { Text(groups[index].label) }
                )
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(currentItems) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
