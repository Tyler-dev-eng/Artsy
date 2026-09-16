package com.tylerdev.artshelf.presentation.screens.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CropFree
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.tylerdev.artshelf.presentation.components.drawHardOffsetShadow
import com.tylerdev.artshelf.presentation.ui.theme.AntonFontFamily
import com.tylerdev.artshelf.presentation.ui.theme.GalleryWhite
import com.tylerdev.artshelf.presentation.ui.theme.Graphite
import com.tylerdev.artshelf.presentation.ui.theme.InkBlack
import com.tylerdev.artshelf.presentation.ui.theme.OnSurfaceVariant
import com.tylerdev.artshelf.presentation.ui.theme.PaperCream
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed
import com.tylerdev.artshelf.presentation.ui.theme.SunflowerYellow
import com.tylerdev.artshelf.presentation.ui.theme.SurfaceContainer
import com.tylerdev.artshelf.presentation.ui.theme.SurfaceContainerLow

private val SCREEN_MARGIN = 20.dp
private val SECTION_GAP = 24.dp
private val HEADER_SHADOW_OFFSET = 4.dp
private val TAPE_WIDTH = 56.dp
private val TAPE_HEIGHT = 20.dp

private val HeadlineLgMobile = TextStyle(
    fontFamily = AntonFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 32.sp,
    lineHeight = 36.sp,
    letterSpacing = (-0.01).em,
)

@Suppress("ktlint:standard:function-naming")
@Composable
fun LibraryEmptyState(
    modifier: Modifier = Modifier,
    onExploreClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
    ) {
        CollectionHeaderBand()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal = SCREEN_MARGIN),
        ) {
            Spacer(modifier = Modifier.height(SECTION_GAP))
            EmptyWallFrame()
            Spacer(modifier = Modifier.height(SECTION_GAP))
            EmptyGalleryHeadline()
            Spacer(modifier = Modifier.height(16.dp))
            EmptyGalleryBody()
            Spacer(modifier = Modifier.height(SECTION_GAP))
            CuratorNoteCard()
            Spacer(modifier = Modifier.height(SECTION_GAP))
            ExploreDiscoverButton(onClick = onExploreClick)
            Spacer(modifier = Modifier.height(SECTION_GAP))
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun CollectionHeaderBand() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PaperCream)
            .padding(horizontal = SCREEN_MARGIN, vertical = 20.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "YOUR COLLECTION",
                style = HeadlineLgMobile,
                color = GalleryWhite,
                modifier = Modifier
                    .graphicsLayer { rotationZ = -4f }
                    .drawHardOffsetShadow(HEADER_SHADOW_OFFSET, SignalRed)
                    .background(InkBlack)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
            )
            PiecesCuratedBadge()
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(width = 16.dp, height = 4.dp)
                    .graphicsLayer { rotationZ = -12f }
                    .background(SignalRed),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "PRIVATE EXHIBITION • VAULT 00",
                style = MaterialTheme.typography.labelMedium,
                color = Graphite,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .graphicsLayer { rotationZ = -1f }
                .background(SignalRed),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun PiecesCuratedBadge() {
    Box(
        modifier = Modifier
            .graphicsLayer { rotationZ = 3f }
            .drawHardOffsetShadow(3.dp, InkBlack)
            .background(SignalRed)
            .padding(horizontal = 14.dp, vertical = 6.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "0 PIECES",
                style = MaterialTheme.typography.labelSmall,
                color = GalleryWhite,
            )
            Text(
                text = "CURATED",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = SunflowerYellow,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun EmptyWallFrame() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    rotationZ = -2f
                    translationX = 8.dp.toPx()
                    translationY = 10.dp.toPx()
                }
                .background(SignalRed)
                .height(280.dp),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer { rotationZ = -1f }
                .background(PaperCream)
                .padding(16.dp),
        ) {
            WallFrameAperture()
            Spacer(modifier = Modifier.height(12.dp))
            WallPlaqueDetail()
        }
        TapeStub(rotation = -18f, alignment = Alignment.TopStart, offsetX = (-8).dp, offsetY = (-6).dp)
        TapeStub(rotation = 22f, alignment = Alignment.TopEnd, offsetX = 8.dp, offsetY = (-4).dp)
        TapeStub(rotation = -10f, alignment = Alignment.BottomEnd, offsetX = 8.dp, offsetY = 6.dp)
        TapeStub(rotation = 15f, alignment = Alignment.BottomStart, offsetX = (-10).dp, offsetY = 4.dp)
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun WallFrameAperture() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(4f / 3f)
            .background(SurfaceContainerLow),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .graphicsLayer { rotationZ = -6f }
                    .background(SurfaceContainer, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.CropFree,
                    contentDescription = null,
                    tint = SunflowerYellow,
                    modifier = Modifier.size(28.dp),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "BLANK CANVAS",
                style = MaterialTheme.typography.headlineLarge,
                color = GalleryWhite,
                modifier = Modifier
                    .graphicsLayer { rotationZ = 3f }
                    .background(SignalRed)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "[ EXHIBITION PENDING ]",
                style = MaterialTheme.typography.labelSmall,
                color = OnSurfaceVariant,
            )
        }
        Text(
            text = "NO. 00-00",
            style = MaterialTheme.typography.labelSmall,
            color = OnSurfaceVariant.copy(alpha = 0.4f),
            modifier = Modifier.align(Alignment.TopStart).padding(8.dp),
        )
        Text(
            text = "40.7128° N",
            style = MaterialTheme.typography.labelSmall,
            color = OnSurfaceVariant.copy(alpha = 0.4f),
            modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun WallPlaqueDetail() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Text(
                text = "RESERVED WALL #01",
                style = MaterialTheme.typography.headlineLarge,
                color = InkBlack,
            )
            Text(
                text = "Private Vault Edition",
                style = MaterialTheme.typography.bodySmall,
                color = Graphite,
            )
        }
        Text(
            text = "ACQUISITION: 0",
            style = MaterialTheme.typography.labelSmall,
            color = GalleryWhite,
            modifier = Modifier
                .background(InkBlack)
                .padding(horizontal = 6.dp, vertical = 2.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun BoxScope.TapeStub(
    rotation: Float,
    alignment: Alignment,
    offsetX: Dp,
    offsetY: Dp,
) {
    Box(
        modifier = Modifier
            .align(alignment)
            .graphicsLayer {
                rotationZ = rotation
                translationX = offsetX.toPx()
                translationY = offsetY.toPx()
            }
            .size(width = TAPE_WIDTH, height = TAPE_HEIGHT)
            .background(SunflowerYellow.copy(alpha = 0.8f)),
    )
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun EmptyGalleryHeadline() {
    Text(
        text = "YOUR GALLERY IS EMPTY —\nGO FIND SOMETHING WORTH KEEPING",
        style = HeadlineLgMobile,
        color = GalleryWhite,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .graphicsLayer { rotationZ = -3f }
            .background(InkBlack)
            .padding(horizontal = 8.dp, vertical = 6.dp),
    )
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun EmptyGalleryBody() {
    Text(
        text = "Your personal exhibition vault has zero catalogued artifacts. " +
            "Scour the discover feed, claim revolutionary pieces, and bind your private zine collection.",
        style = MaterialTheme.typography.bodyMedium,
        color = OnSurfaceVariant,
        textAlign = TextAlign.Center,
    )
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun CuratorNoteCard() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .graphicsLayer { rotationZ = 1f }
                .background(SignalRed),
        )
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .background(PaperCream)
                .padding(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .graphicsLayer { rotationZ = -6f }
                    .background(SunflowerYellow),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = InkBlack,
                    modifier = Modifier.size(20.dp),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "CURATOR'S NOTE",
                    style = MaterialTheme.typography.labelSmall,
                    color = SignalRed,
                )
                Text(
                    text = "Tap the star icon on any artwork in Discover or Detail to pin it directly to this wall.",
                    style = MaterialTheme.typography.bodySmall,
                    color = InkBlack,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ExploreDiscoverButton(onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { rotationZ = -2f }
            .drawHardOffsetShadow(HEADER_SHADOW_OFFSET, InkBlack)
            .background(SignalRed)
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
    ) {
        Text(
            text = "EXPLORE DISCOVER FEED",
            style = MaterialTheme.typography.labelLarge,
            color = GalleryWhite,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = GalleryWhite,
            modifier = Modifier.size(20.dp),
        )
    }
}
