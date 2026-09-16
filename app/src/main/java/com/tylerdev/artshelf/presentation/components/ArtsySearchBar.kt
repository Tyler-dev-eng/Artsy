package com.tylerdev.artshelf.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.tylerdev.artshelf.presentation.ui.theme.Background
import com.tylerdev.artshelf.presentation.ui.theme.GalleryWhite
import com.tylerdev.artshelf.presentation.ui.theme.Graphite
import com.tylerdev.artshelf.presentation.ui.theme.InkBlack
import com.tylerdev.artshelf.presentation.ui.theme.PaperCream
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed

private val SEARCH_BAR_OUTER_PADDING = 6.dp
private val SEARCH_BAR_START_PADDING = 16.dp
private val SEARCH_BAR_END_PADDING = 4.dp
private val SEARCH_BAR_CONTENT_GAP = 8.dp
private val SEARCH_BAR_SHADOW_OFFSET = 4.dp
private val SEARCH_ICON_SIZE = 22.dp
private val NOTCH_DIAMETER = 20.dp
private val NOTCH_INSET = NOTCH_DIAMETER / 2

private val SUBMIT_BUTTON_TOUCH_TARGET = 48.dp
private val SUBMIT_BUTTON_VISUAL_SIZE = 32.dp
private val SUBMIT_BUTTON_SHADOW_OFFSET = 2.dp
private val SUBMIT_BUTTON_ICON_SIZE = 18.dp
private const val SUBMIT_BUTTON_ROTATION_DEGREES = -2f

private val QUERY_TEXT_LETTER_SPACING = (-0.01).em

@Suppress("ktlint:standard:function-naming")
@Composable
fun ArtsySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearchSubmit: () -> Unit = {},
    placeholder: String = "search for art…",
    notchColor: Color = Background,
) {
    Box(modifier = modifier.fillMaxWidth().padding(top = 20.dp)) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .drawHardOffsetShadow(SEARCH_BAR_SHADOW_OFFSET, InkBlack)
                    .background(PaperCream)
                    .padding(SEARCH_BAR_OUTER_PADDING)
                    .padding(start = SEARCH_BAR_START_PADDING, end = SEARCH_BAR_END_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(SEARCH_BAR_CONTENT_GAP),
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = InkBlack,
                modifier = Modifier.size(SEARCH_ICON_SIZE),
            )
            SearchBarInput(
                query = query,
                onQueryChange = onQueryChange,
                placeholder = placeholder,
                modifier = Modifier.weight(1f),
            )
            SearchSubmitButton(onClick = onSearchSubmit)
        }
        SearchBarNotch(
            color = notchColor,
            modifier =
                Modifier.align(Alignment.CenterStart).graphicsLayer {
                    translationX =
                        -NOTCH_INSET.toPx()
                },
        )
        SearchBarNotch(
            color = notchColor,
            modifier = Modifier.align(Alignment.CenterEnd).graphicsLayer { translationX = NOTCH_INSET.toPx() },
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SearchBarInput(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        if (query.isEmpty()) {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = Graphite,
            )
        }
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.merge(MaterialTheme.typography.bodyMedium).copy(
                color = InkBlack,
                fontWeight = FontWeight.Medium,
                letterSpacing = QUERY_TEXT_LETTER_SPACING,
            ),
            cursorBrush = SolidColor(SignalRed),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SearchSubmitButton(onClick: () -> Unit) {
    Box(
        modifier =
            Modifier
                .size(SUBMIT_BUTTON_TOUCH_TARGET)
                .clickable(onClick = onClick)
                .semantics { contentDescription = "Submit search" },
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier =
                Modifier
                    .size(SUBMIT_BUTTON_VISUAL_SIZE)
                    .graphicsLayer { rotationZ = SUBMIT_BUTTON_ROTATION_DEGREES }
                    .drawHardOffsetShadow(SUBMIT_BUTTON_SHADOW_OFFSET, InkBlack)
                    .background(SignalRed),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = GalleryWhite,
                modifier = Modifier.size(SUBMIT_BUTTON_ICON_SIZE),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SearchBarNotch(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(NOTCH_DIAMETER)
                .background(color, CircleShape),
    )
}
