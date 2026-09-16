package com.tylerdev.artshelf.presentation.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.drawHardOffsetShadow(
    offset: Dp,
    color: Color,
): Modifier = drawBehind {
    val offsetPx = offset.toPx()
    drawRect(color = color, topLeft = Offset(offsetPx, offsetPx), size = size)
}
