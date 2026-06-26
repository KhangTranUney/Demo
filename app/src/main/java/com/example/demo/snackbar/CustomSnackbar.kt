package com.example.demo.snackbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class SnackbarLayout { Horizontal, Vertical }

/** Plain data carrier for rendering CustomSnackbar manually, outside of SnackbarHost. */
data class DSSnackbarData(
    val message: String,
    val actionLabel: String? = null,
    val layout: SnackbarLayout = SnackbarLayout.Horizontal,
    val onAction: () -> Unit = {},
)

/** Material3 SnackbarVisuals with a layout field, for use with SnackbarHostState.showSnackbar(...). */
data class CustomSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false,
    val layout: SnackbarLayout = SnackbarLayout.Horizontal,
) : SnackbarVisuals

/** Overload for the default SnackbarHost slot. Bridges SnackbarData → DSSnackbarData. */
@Composable
fun CustomSnackbar(data: SnackbarData) {
    val visuals = data.visuals
    val layout = (visuals as? CustomSnackbarVisuals)?.layout ?: SnackbarLayout.Horizontal
    CustomSnackbar(
        DSSnackbarData(
            message = visuals.message,
            actionLabel = visuals.actionLabel,
            layout = layout,
            onAction = { data.performAction() }
        )
    )
}

/** Overload for manual use, e.g. as a standalone composable in any screen. */
@Composable
fun CustomSnackbar(data: DSSnackbarData, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
    ) {
        when (data.layout) {
            SnackbarLayout.Horizontal -> Row(
                modifier = Modifier.padding(
                    PaddingValues(start = 16.dp, end = 8.dp, top = 12.dp, bottom = 12.dp)
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LeadingIcon()
                Spacer(Modifier.width(12.dp))
                Text(
                    text = data.message,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                if (data.actionLabel != null) {
                    TextButton(onClick = data.onAction) { Text(data.actionLabel) }
                }
            }

            SnackbarLayout.Vertical -> Column(
                modifier = Modifier.padding(
                    PaddingValues(start = 16.dp, end = 8.dp, top = 12.dp, bottom = 8.dp)
                )
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    LeadingIcon()
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp, top = 2.dp)
                    )
                }
                if (data.actionLabel != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = data.onAction) { Text(data.actionLabel) }
                    }
                }
            }
        }
    }
}

@Composable
private fun LeadingIcon() {
    Icon(
        imageVector = Icons.Outlined.Info,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
