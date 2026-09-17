package com.tylerdev.artshelf.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.dp
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
import com.tylerdev.artshelf.presentation.ui.theme.SurfaceContainerHigh

private val SCREEN_MARGIN = 20.dp
private val SECTION_GAP = 24.dp
private val SHADOW_OFFSET = 4.dp
private val TAG_TOUCH_TARGET = 48.dp
private const val TAGS_PER_ROW = 2
private val DEFAULT_RADAR_TAGS = listOf(
    "NEO-PUNK",
    "EXPRESSIONISM",
    "BRUTALISM",
    "JAPANESE WOODBLOCK",
    "ABSTRACT GRAFFITI",
)

private val HeadlineLgMobile = TextStyle(
    fontFamily = AntonFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 32.sp,
    lineHeight = 36.sp,
    letterSpacing = (-0.01).em,
)

@Suppress("ktlint:standard:function-naming")
@Composable
fun SearchScreenEmptyState(
    modifier: Modifier = Modifier,
    radarTags: List<String> = DEFAULT_RADAR_TAGS,
    onTagClick: (String) -> Unit = {},
    onClearAndExploreClick: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = SCREEN_MARGIN),
    ) {
        Spacer(modifier = Modifier.height(SECTION_GAP))
        VoidCollageStage()
        Spacer(modifier = Modifier.height(16.dp))
        SignalLostBadge()
        Spacer(modifier = Modifier.height(4.dp))
        VoidHeadline()
        Spacer(modifier = Modifier.height(16.dp))
        CuratorExplanationCard()
        Spacer(modifier = Modifier.height(SECTION_GAP))
        DisruptionArchiveDivider()
        Spacer(modifier = Modifier.height(SECTION_GAP))
        HotSignalsSection(tags = radarTags, onTagClick = onTagClick)
        Spacer(modifier = Modifier.height(SECTION_GAP))
        ClearSearchExploreButton(onClick = onClearAndExploreClick)
        Spacer(modifier = Modifier.height(8.dp))
        ArchiveCodeFooter()
        Spacer(modifier = Modifier.height(SECTION_GAP))
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun VoidCollageStage() {
    Box(modifier = Modifier.fillMaxWidth().aspectRatio(4f / 3f)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
                .graphicsLayer { rotationZ = 3f }
                .background(Graphite),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
                .graphicsLayer { rotationZ = -2f }
                .background(InkBlack),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.HideImage,
                contentDescription = null,
                tint = Graphite,
                modifier = Modifier.size(56.dp),
            )
            OfflineTapeSticker(modifier = Modifier.align(Alignment.TopStart).padding(start = 12.dp, top = 4.dp))
            NullSignatureTapeSticker(
                modifier = Modifier.align(Alignment.BottomEnd).padding(end = 8.dp, bottom = 8.dp),
            )
            Box(
                modifier = Modifier
                    .graphicsLayer { rotationZ = -6f }
                    .drawHardOffsetShadow(3.dp, InkBlack)
                    .background(SignalRed)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
            ) {
                Text(
                    text = "ZERO MATCH",
                    style = MaterialTheme.typography.headlineLarge,
                    color = GalleryWhite,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun OfflineTapeSticker(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .graphicsLayer { rotationZ = -6f }
            .background(SunflowerYellow.copy(alpha = 0.9f))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    ) {
        Text(
            text = "OFFLINE",
            style = MaterialTheme.typography.labelSmall,
            color = InkBlack,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun NullSignatureTapeSticker(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .graphicsLayer { rotationZ = 3f }
            .background(PaperCream.copy(alpha = 0.9f))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    ) {
        Text(
            text = "NULL SIGNATURE",
            style = MaterialTheme.typography.labelSmall,
            color = SignalRed,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SignalLostBadge() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { rotationZ = -3f },
    ) {
        Box(modifier = Modifier.background(SignalRed).padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text(
                text = "STATUS // SIGNAL LOST",
                style = MaterialTheme.typography.labelSmall,
                color = GalleryWhite,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun VoidHeadline() {
    Text(
        text = "VOID DETECTED // ZERO CANVASES",
        style = HeadlineLgMobile,
        color = GalleryWhite,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { rotationZ = -3f },
    )
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun CuratorExplanationCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawHardOffsetShadow(SHADOW_OFFSET, Graphite)
            .background(SurfaceContainer)
            .padding(16.dp),
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .background(SunflowerYellow),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Our curators scanned the underground archives, but this frequency came up " +
                "completely blank. Broaden your manifesto cues or hijack one of our trending " +
                "radar tags below.",
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun DisruptionArchiveDivider() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { rotationZ = -3f }
            .background(SignalRed)
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 6.dp, horizontal = 12.dp),
    ) {
        DisruptionArchiveLabel(text = "DISRUPTION ARCHIVE", color = GalleryWhite)
        DisruptionArchiveLabel(text = "ZINE ISSUE #09", color = InkBlack)
        DisruptionArchiveLabel(text = "RADAR ACTIVE", color = GalleryWhite)
        DisruptionArchiveLabel(text = "UNCONVENTIONAL MANIFESTO", color = InkBlack)
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun DisruptionArchiveLabel(
    text: String,
    color: androidx.compose.ui.graphics.Color,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = color,
    )
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun HotSignalsSection(
    tags: List<String>,
    onTagClick: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Bolt,
                    contentDescription = null,
                    tint = SunflowerYellow,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "HOT SIGNALS / RADAR TAGS",
                    style = MaterialTheme.typography.labelLarge,
                    color = SunflowerYellow,
                )
            }
            Text(
                text = "TOUCH TO INJECT",
                style = MaterialTheme.typography.labelSmall,
                color = GalleryWhite.copy(alpha = 0.6f),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        RadarTagsFlow(tags = tags, onTagClick = onTagClick)
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun RadarTagsFlow(
    tags: List<String>,
    onTagClick: (String) -> Unit,
) {
    val chipStyles = listOf(
        ChipStyle(background = PaperCream, content = InkBlack, shadow = SignalRed, rotation = -2f),
        ChipStyle(background = SignalRed, content = GalleryWhite, shadow = InkBlack, rotation = 2f),
        ChipStyle(background = PaperCream, content = InkBlack, shadow = SunflowerYellow, rotation = -1f),
        ChipStyle(background = SurfaceContainerHigh, content = GalleryWhite, shadow = SignalRed, rotation = 3f),
        ChipStyle(background = SunflowerYellow, content = InkBlack, shadow = InkBlack, rotation = -3f),
    )
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        tags.chunked(2).forEachIndexed { rowIndex, rowTags ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                rowTags.forEachIndexed { columnIndex, tag ->
                    val styleIndex = rowIndex * TAGS_PER_ROW + columnIndex
                    RadarTagChip(
                        tag = tag,
                        style = chipStyles[styleIndex % chipStyles.size],
                        onClick = { onTagClick(tag) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

private data class ChipStyle(
    val background: androidx.compose.ui.graphics.Color,
    val content: androidx.compose.ui.graphics.Color,
    val shadow: androidx.compose.ui.graphics.Color,
    val rotation: Float,
)

@Suppress("ktlint:standard:function-naming")
@Composable
private fun RadarTagChip(
    tag: String,
    style: ChipStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .defaultMinSize(minHeight = TAG_TOUCH_TARGET)
            .clickable(onClick = onClick)
            .semantics { contentDescription = "Search tag $tag" },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer { rotationZ = style.rotation }
                .drawHardOffsetShadow(3.dp, style.shadow)
                .background(style.background)
                .padding(horizontal = 12.dp, vertical = 6.dp),
        ) {
            Text(
                text = "#$tag",
                style = MaterialTheme.typography.labelMedium,
                color = style.content,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ClearSearchExploreButton(onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = TAG_TOUCH_TARGET)
            .graphicsLayer { rotationZ = -2f }
            .drawHardOffsetShadow(5.dp, PaperCream)
            .background(SignalRed)
            .clickable(onClick = onClick)
            .semantics { contentDescription = "Clear search and explore vault" }
            .padding(vertical = 14.dp),
    ) {
        Text(
            text = "CLEAR SEARCH & EXPLORE VAULT",
            style = MaterialTheme.typography.headlineLarge,
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

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArchiveCodeFooter() {
    Text(
        text = "ARCHIVE CODE: 902-VOID-CLR",
        style = MaterialTheme.typography.labelSmall,
        color = GalleryWhite.copy(alpha = 0.5f),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}
