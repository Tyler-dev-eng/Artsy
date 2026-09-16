package com.tylerdev.artshelf.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.tylerdev.artshelf.presentation.ui.theme.Background
import com.tylerdev.artshelf.presentation.ui.theme.GalleryWhite
import com.tylerdev.artshelf.presentation.ui.theme.InkBlack
import com.tylerdev.artshelf.presentation.ui.theme.OnSurfaceVariant
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed
import com.tylerdev.artshelf.presentation.ui.theme.SunflowerYellow

private const val LOGO_MARK_ROTATION_DEGREES = -18f

@Suppress("ktlint:standard:function-naming")
@Composable
fun ArtShelfTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit = {},
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Background)
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ArtsyWordmark()
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = GalleryWhite,
                    modifier = Modifier.weight(1f),
                )
                ProfileAvatar(onClick = onProfileClick)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .drawWithCache {
                        val gradient = Brush.horizontalGradient(
                            colors = listOf(SignalRed, SunflowerYellow),
                            startX = 0f,
                            endX = size.width,
                        )
                        onDrawBehind { drawRect(gradient) }
                    },
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtsyWordmark() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(width = 10.dp, height = 16.dp)
                .rotate(LOGO_MARK_ROTATION_DEGREES)
                .background(SignalRed),
        )
        Text(
            text = "ARTSY",
            style = MaterialTheme.typography.labelLarge,
            color = GalleryWhite,
            modifier = Modifier.padding(start = 4.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ProfileAvatar(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(OnSurfaceVariant)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Profile",
            tint = InkBlack,
            modifier = Modifier.size(22.dp),
        )
    }
}
