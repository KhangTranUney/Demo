package com.example.kinshield.easteregg

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KSEasterEggOverlay(
    content: @Composable () -> Unit,
    onAction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        content()

        IconButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(32.dp),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Adb,
                contentDescription = "Easter Egg",
                modifier = Modifier.size(18.dp)
            )
        }

        if (showDialog) {
            KSEasterEggDialog(
                onAction = onAction,
                onDismiss = { showDialog = false }
            )
        }
    }
}
