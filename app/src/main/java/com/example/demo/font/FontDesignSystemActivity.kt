package com.example.demo.font

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.demo.font.designsystem.DSFontFamilyImpl
import com.example.demo.font.designsystem.DSFontSizeImpl
import com.example.demo.font.designsystem.DSFontWeightImpl
import com.example.demo.font.designsystem.DSTypographyImpl
import com.example.demo.font.designsystem.LocalDSTypography
import com.example.demo.ui.BackTopBar
import com.example.ui.theme.DemoTheme

class FontDesignSystemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                val context = LocalContext.current
                val dsFontSize = DSFontSizeImpl(context)
                val dsFontWeight = DSFontWeightImpl(context)
                val dsFontFamily = DSFontFamilyImpl(useSystemFonts = false)
                val dsTypography = DSTypographyImpl(dsFontSize, dsFontWeight, dsFontFamily)

                CompositionLocalProvider(
                    LocalDSTypography provides dsTypography
                ) {
                    FontDesignSystemScreen()
                }
            }
        }
    }
}

@Composable
private fun FontDesignSystemScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { BackTopBar(title = "Font Design System") },
    ) { innerPadding ->
        val typography = LocalDSTypography.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = "Heading XXL Medium",
                style = typography.headingXxlMedium
            )

            Text(
                text = "Heading XXL SemiBold",
                style = typography.headingXxlSemiBold
            )

            Text(
                text = "Heading XL Medium",
                style = typography.headingXlMedium
            )

            Text(
                text = "Heading XL SemiBold",
                style = typography.headingXlSemiBold
            )

            Text(
                text = "Body L Regular",
                style = typography.bodyLRegular
            )

            Text(
                text = "Body L Medium",
                style = typography.bodyLMedium
            )

            Text(
                text = "Avatar 16 Base",
                style = typography.avatarSize16Base
            )

            Text(
                text = "Avatar 20 Base",
                style = typography.avatarSize20Base
            )
        }
    }
}