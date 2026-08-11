package com.example.demo.font.designsystem

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.example.R

interface DSFontFamily {
    fun heading(fontWeight: FontWeight): FontFamily
    fun body(fontWeight: FontWeight): FontFamily
}

@OptIn(ExperimentalTextApi::class)
class DSFontFamilyImpl(private val useSystemFonts: Boolean) : DSFontFamily {

    private val headingFontResId = R.font.zalando_sans_variable_wdth_wght
    private val bodyFontResId = R.font.zalando_sans_variable_wdth_wght

    override fun heading(fontWeight: FontWeight): FontFamily {
        return createFontFamily(headingFontResId, fontWeight)
    }

    override fun body(fontWeight: FontWeight): FontFamily {
        return createFontFamily(bodyFontResId, fontWeight)
    }

    private fun createFontFamily(fontResId: Int, fontWeight: FontWeight): FontFamily {
        return if (!useSystemFonts) {
            FontFamily(
                Font(
                    resId = fontResId,
                    variationSettings = FontVariation.Settings(
                        weight = fontWeight,
                        style = FontStyle.Normal
                    ),
                )
            )
        } else FontFamily.Default
    }
}