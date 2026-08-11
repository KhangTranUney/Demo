package com.example.demo.font.designsystem

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

internal val LocalDSTypography = staticCompositionLocalOf<DSTypography> {
    error("No Typography provided")
}

interface DSTypography {
    val headingXxlMedium: TextStyle
    val headingXxlSemiBold: TextStyle
    val headingXlMedium: TextStyle
    val headingXlSemiBold: TextStyle
    val bodyLRegular: TextStyle
    val bodyLMedium: TextStyle
    val avatarSize16Base: TextStyle
    val avatarSize20Base: TextStyle
}

class DSTypographyImpl(
    dsFontSize: DSFontSize,
    dsFontWeight: DSFontWeight,
    dsFontFamily: DSFontFamily
) : DSTypography {

    override val headingXxlMedium: TextStyle =
        TextStyle(
            fontFamily = dsFontFamily.heading(dsFontWeight.headingMedium),
            fontSize = dsFontSize.sizeHeadingXxl,
            lineHeight = dsFontSize.lineHeightHeadingXxl,
        )
    override val headingXxlSemiBold: TextStyle =
        TextStyle(
            fontFamily = dsFontFamily.heading(dsFontWeight.headingSemiBold),
            fontSize = dsFontSize.sizeHeadingXxl,
            lineHeight = dsFontSize.lineHeightHeadingXxl,
        )
    override val headingXlMedium: TextStyle =
        TextStyle(
            fontFamily = dsFontFamily.heading(dsFontWeight.headingMedium),
            fontSize = dsFontSize.sizeHeadingXl,
            lineHeight = dsFontSize.lineHeightHeadingXl,
        )
    override val headingXlSemiBold: TextStyle =
        TextStyle(
            fontFamily = dsFontFamily.heading(dsFontWeight.headingSemiBold),
            fontSize = dsFontSize.sizeHeadingXl,
            lineHeight = dsFontSize.lineHeightHeadingXl,
        )

    override val bodyLRegular: TextStyle =
        TextStyle(
            fontFamily = dsFontFamily.heading(dsFontWeight.bodyRegular),
            fontSize = dsFontSize.sizeBodyL,
            lineHeight = dsFontSize.lineHeightBodyL,
        )
    override val bodyLMedium: TextStyle =
        TextStyle(
            fontFamily = dsFontFamily.heading(dsFontWeight.bodyMedium),
            fontSize = dsFontSize.sizeBodyL,
            lineHeight = dsFontSize.lineHeightBodyL,
        )
    override val avatarSize16Base: TextStyle =
        TextStyle(
            fontFamily = dsFontFamily.heading(dsFontWeight.avatarBase),
            fontSize = dsFontSize.sizeAvatarSize16,
        )
    override val avatarSize20Base: TextStyle =
        TextStyle(
            fontFamily = dsFontFamily.heading(dsFontWeight.avatarBase),
            fontSize = dsFontSize.sizeAvatarSize20,
        )
}