package com.example.demo.font

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.FontRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.demo.ui.BackTopBar
import com.example.ui.theme.DemoTheme

class FontTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                FontTestScreen()
            }
        }
    }
}

private data class TestFont(
    val name: String,
    @param:FontRes val resourceId: Int,
    val style: FontStyle = FontStyle.Normal,
)

private val testFonts = listOf(
    TestFont("Inter", R.font.inter_variable_opsz_wght),
    TestFont("Inter Italic", R.font.inter_italic_variable_opsz_wght, FontStyle.Italic),
    TestFont("Noto Sans", R.font.noto_sans_variable_wdth_wght),
    TestFont("Noto Sans Italic", R.font.noto_sans_italic_variable_wdth_wght, FontStyle.Italic),
    TestFont("Zalando Sans", R.font.zalando_sans_variable_wdth_wght),
    TestFont(
        "Zalando Sans Italic",
        R.font.zalando_sans_italic_variable_wdth_wght,
        FontStyle.Italic
    ),
)

@Composable
private fun FontTestScreen() {
    var selectedFontIndex by remember { mutableIntStateOf(0) }
    var fontWeight by remember { mutableFloatStateOf(400f) }
    var showFontPicker by remember { mutableStateOf(false) }
    val selectedFont = testFonts[selectedFontIndex]
    val fontFamily = remember(selectedFont.resourceId, fontWeight) {
        variableFontFamily(
            resourceId = selectedFont.resourceId,
            style = selectedFont.style,
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { BackTopBar(title = "Variable Font Test") },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Button(onClick = { showFontPicker = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Font: ${selectedFont.name}")
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Weight")
                    Text(fontWeight.toInt().toString())
                }
                Slider(
                    value = fontWeight,
                    onValueChange = { fontWeight = it },
                    valueRange = 100f..900f,
                )
            }

            HorizontalDivider()

            Text(
                text = "Hello",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontFamily = fontFamily,
                fontSize = 72.sp,
                fontWeight = FontWeight(fontWeight.toInt()),
                fontStyle = selectedFont.style,
            )
        }
    }

    if (showFontPicker) {
        AlertDialog(
            onDismissRequest = { showFontPicker = false },
            title = { Text("Select a font") },
            text = {
                Column {
                    testFonts.forEachIndexed { index, font ->
                        TextButton(
                            onClick = {
                                selectedFontIndex = index
                                showFontPicker = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(font.name, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showFontPicker = false }) { Text("Cancel") }
            },
        )
    }
}

private fun variableFontFamily(
    @FontRes resourceId: Int,
    style: FontStyle,
): FontFamily = FontFamily(
    Font(
        resId = resourceId,
        style = style,
    )
)
