package com.example.demo.font.designsystem

import android.content.Context
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Auto generated
 */

interface DSFontSize {
    val lineHeightHeadingXxl: TextUnit
    val lineHeightHeadingXl: TextUnit
    val lineHeightBodyL: TextUnit
    val lineHeightBodyM: TextUnit
    val sizeHeadingXxl: TextUnit
    val sizeHeadingXl: TextUnit
    val sizeBodyL: TextUnit
    val sizeBodyM: TextUnit
    val sizeAvatarSize16: TextUnit
    val sizeAvatarSize20: TextUnit
}

class DSFontSizeImpl(context: Context) : DSFontSize {
    override val lineHeightHeadingXxl: TextUnit = 54.sp
    override val lineHeightHeadingXl: TextUnit = 42.sp
    override val lineHeightBodyL: TextUnit = 26.sp
    override val lineHeightBodyM: TextUnit = 24.sp
    override val sizeHeadingXxl: TextUnit = 48.sp
    override val sizeHeadingXl: TextUnit = 36.sp
    override val sizeBodyL: TextUnit = 18.sp
    override val sizeBodyM: TextUnit = 16.sp
    override val sizeAvatarSize16: TextUnit = 8.sp
    override val sizeAvatarSize20: TextUnit = 10.sp
}