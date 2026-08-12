package com.example.demo.font.designsystem

import android.content.Context
import androidx.compose.ui.text.font.FontWeight

/**
 * Auto generated
 */

interface DSFontWeight {
    val headingMedium: FontWeight
    val headingSemiBold: FontWeight
    val bodyRegular: FontWeight
    val bodyMedium: FontWeight
    val avatarBase: FontWeight
}

class DSFontWeightImpl(context: Context) : DSFontWeight {
    override val headingMedium: FontWeight = FontWeight(500)
    override val headingSemiBold: FontWeight = FontWeight(600)
    override val bodyRegular: FontWeight = FontWeight(400)
    override val bodyMedium: FontWeight = FontWeight(500)
    override val avatarBase: FontWeight = FontWeight(700)
}