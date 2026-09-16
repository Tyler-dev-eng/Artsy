package com.tylerdev.artshelf.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.tylerdev.artshelf.R

@OptIn(ExperimentalTextApi::class)
private fun workSansWeight(weight: Int) = Font(
    resId = R.font.work_sans_variable,
    weight = FontWeight(weight),
    variationSettings = FontVariation.Settings(FontVariation.weight(weight)),
)

val AntonFontFamily = FontFamily(Font(R.font.anton_regular, FontWeight.Normal))

val WorkSansFontFamily = FontFamily(
    workSansWeight(400),
    workSansWeight(500),
    workSansWeight(600),
    workSansWeight(700),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AntonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 56.sp,
        lineHeight = 56.sp,
        letterSpacing = (-0.03).em,
    ),
    displayMedium = TextStyle(
        fontFamily = AntonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.02).em,
    ),
    displaySmall = TextStyle(
        fontFamily = AntonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 32.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = AntonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 26.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = WorkSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = WorkSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = WorkSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = WorkSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = WorkSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = WorkSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = AntonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.05.em,
    ),
    labelMedium = TextStyle(
        fontFamily = WorkSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.02.em,
    ),
    labelSmall = TextStyle(
        fontFamily = WorkSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.04.em,
    ),
)
