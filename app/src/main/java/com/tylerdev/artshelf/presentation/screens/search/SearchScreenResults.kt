package com.tylerdev.artshelf.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.presentation.components.drawHardOffsetShadow
import com.tylerdev.artshelf.presentation.ui.theme.GalleryWhite
import com.tylerdev.artshelf.presentation.ui.theme.Graphite
import com.tylerdev.artshelf.presentation.ui.theme.InkBlack
import com.tylerdev.artshelf.presentation.ui.theme.PaperCream
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed
import com.tylerdev.artshelf.presentation.ui.theme.SunflowerYellow
import com.tylerdev.artshelf.presentation.ui.theme.SurfaceContainerHigh
import com.tylerdev.artshelf.presentation.ui.theme.SurfaceContainerHighest

private val SCREEN_MARGIN = 20.dp
private val SECTION_GAP = 24.dp
private val TOUCH_TARGET = 48.dp
private val DEFAULT_CATEGORIES = listOf("All Works", "Paintings", "Sculpture", "Photography", "Surrealism")

private sealed class FeedGroup {
    data class Feature(
        val art: ArtImage,
    ) : FeedGroup()

    data class Pair(
        val first: ArtImage,
        val second: ArtImage,
    ) : FeedGroup()

    data class Banner(
        val art: ArtImage,
    ) : FeedGroup()
}

private fun buildFeedGroups(artImages: List<ArtImage>): List<FeedGroup> {
    val groups = mutableListOf<FeedGroup>()
    var cursor = 0
    var step = 0
    while (cursor < artImages.size) {
        val remaining = artImages.size - cursor
        when (step % 3) {
            1 -> {
                if (remaining >= 2) {
                    groups += FeedGroup.Pair(artImages[cursor], artImages[cursor + 1])
                    cursor += 2
                } else {
                    groups += FeedGroup.Feature(artImages[cursor])
                    cursor += 1
                }
            }

            2 -> {
                groups += FeedGroup.Banner(artImages[cursor])
                cursor += 1
            }

            else -> {
                groups += FeedGroup.Feature(artImages[cursor])
                cursor += 1
            }
        }
        step += 1
    }
    return groups
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun SearchScreenResults(
    query: String,
    artImages: List<ArtImage>,
    modifier: Modifier = Modifier,
    savedArtIds: Set<Long> = emptySet(),
    onSaveClick: (ArtImage) -> Unit = {},
) {
    val feedGroups = remember(artImages) { buildFeedGroups(artImages) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = SCREEN_MARGIN, vertical = SECTION_GAP),
        verticalArrangement = Arrangement.spacedBy(SECTION_GAP),
    ) {
        item(key = "active-query-banner") {
            ActiveQueryBanner(query = query, resultCount = artImages.size)
        }
        item(key = "category-filter-chips") {
            CategoryFilterChips(categories = DEFAULT_CATEGORIES)
        }
        itemsIndexed(feedGroups, key = { index, _ -> index }, contentType = { _, group -> group::class }) { index, group ->
            Column {
                FeedGroupContent(group = group, savedArtIds = savedArtIds, onSaveClick = onSaveClick)
                if (index != feedGroups.lastIndex) {
                    Spacer1()
                    DiscoveryDividerBand()
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun Spacer1() {
    Box(modifier = Modifier.height(SECTION_GAP))
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun FeedGroupContent(
    group: FeedGroup,
    savedArtIds: Set<Long>,
    onSaveClick: (ArtImage) -> Unit,
) {
    when (group) {
        is FeedGroup.Feature -> {
            FeatureTile(
                art = group.art,
                isSaved = group.art.id in savedArtIds,
                onSaveClick = { onSaveClick(group.art) },
            )
        }

        is FeedGroup.Banner -> {
            BannerTile(
                art = group.art,
                isSaved = group.art.id in savedArtIds,
                onSaveClick = { onSaveClick(group.art) },
            )
        }

        is FeedGroup.Pair -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                PairTile(
                    art = group.first,
                    aspectRatio = 3f / 4f,
                    rotationDegrees = 2f,
                    isSaved = group.first.id in savedArtIds,
                    onSaveClick = { onSaveClick(group.first) },
                    modifier = Modifier.weight(1f),
                )
                PairTile(
                    art = group.second,
                    aspectRatio = 1f,
                    rotationDegrees = -3f,
                    isSaved = group.second.id in savedArtIds,
                    onSaveClick = { onSaveClick(group.second) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ActiveQueryBanner(
    query: String,
    resultCount: Int,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .graphicsLayer { rotationZ = -2f }
                    .drawHardOffsetShadow(4.dp, InkBlack)
                    .background(SignalRed)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "EXHIBIT: ",
                    style = MaterialTheme.typography.labelLarge,
                    color = SunflowerYellow,
                )
                Text(
                    text = query.uppercase(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = GalleryWhite,
                )
            }
            Text(
                text = "$resultCount PIECES",
                style = MaterialTheme.typography.labelSmall,
                color = SunflowerYellow,
                modifier =
                    Modifier
                        .graphicsLayer { rotationZ = -6f }
                        .background(InkBlack)
                        .padding(horizontal = 8.dp, vertical = 3.dp),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun CategoryFilterChips(categories: List<String>) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.horizontalScroll(rememberScrollState()),
    ) {
        categories.forEachIndexed { index, category ->
            val isSelected = index == selectedIndex
            Text(
                text = category,
                style = MaterialTheme.typography.labelLarge,
                color = if (isSelected) InkBlack else PaperCream,
                modifier =
                    Modifier
                        .defaultMinSize(minHeight = TOUCH_TARGET)
                        .graphicsLayer { rotationZ = if (index % 2 == 0) -2f else 1f }
                        .drawHardOffsetShadow(3.dp, if (isSelected) SignalRed else InkBlack)
                        .background(if (isSelected) GalleryWhite else SurfaceContainerHigh)
                        .clickable { selectedIndex = index }
                        .semantics { contentDescription = "Filter by $category" }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun FeatureTile(
    art: ArtImage,
    isSaved: Boolean,
    onSaveClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .graphicsLayer { rotationZ = -1f },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .drawHardOffsetShadow(6.dp, InkBlack)
                    .background(PaperCream)
                    .padding(8.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 5f)
                        .background(InkBlack),
            ) {
                ArtworkImage(art = art, modifier = Modifier.fillMaxSize())
                Text(
                    text = "SPOTLIGHT",
                    style = MaterialTheme.typography.labelSmall,
                    color = GalleryWhite,
                    modifier =
                        Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                            .graphicsLayer { rotationZ = -3f }
                            .background(SignalRed)
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                )
                SaveButton(
                    isSaved = isSaved,
                    onClick = onSaveClick,
                    modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
                )
                ArtworkPlaque(
                    art = art,
                    modifier = Modifier.align(Alignment.BottomStart).padding(12.dp),
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun PairTile(
    art: ArtImage,
    aspectRatio: Float,
    rotationDegrees: Float,
    isSaved: Boolean,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.graphicsLayer { rotationZ = rotationDegrees }) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .drawHardOffsetShadow(4.dp, SignalRed)
                    .background(InkBlack)
                    .padding(6.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(aspectRatio)
                        .background(SurfaceContainerHighest),
            ) {
                ArtworkImage(art = art, modifier = Modifier.fillMaxSize())
                SaveButton(
                    isSaved = isSaved,
                    onClick = onSaveClick,
                    size = TOUCH_TARGET - 8.dp,
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                )
                CompactArtworkPlaque(
                    art = art,
                    modifier =
                        Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .padding(8.dp),
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun BannerTile(
    art: ArtImage,
    isSaved: Boolean,
    onSaveClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .graphicsLayer { rotationZ = 1f }
                .drawHardOffsetShadow(6.dp, SunflowerYellow)
                .background(InkBlack)
                .padding(8.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(SurfaceContainerHigh),
        ) {
            ArtworkImage(art = art, modifier = Modifier.fillMaxSize())
            Text(
                text = "NEW DISCOVERY",
                style = MaterialTheme.typography.labelSmall,
                color = InkBlack,
                modifier =
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .graphicsLayer { rotationZ = 2f }
                        .background(SunflowerYellow)
                        .padding(horizontal = 8.dp, vertical = 2.dp),
            )
            SaveButton(
                isSaved = isSaved,
                onClick = onSaveClick,
                modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
            )
            ArtworkPlaque(
                art = art,
                background = PaperCream,
                content = InkBlack,
                modifier = Modifier.align(Alignment.BottomStart).padding(12.dp),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtworkImage(
    art: ArtImage,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model =
            ImageRequest
                .Builder(LocalContext.current)
                .data(art.webformatUrl)
                .crossfade(true)
                .build(),
        contentDescription = art.tags.firstOrNull(),
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SaveButton(
    isSaved: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = TOUCH_TARGET,
) {
    Box(
        modifier =
            modifier
                .size(size)
                .drawHardOffsetShadow(2.dp, if (isSaved) SunflowerYellow else SignalRed)
                .background(InkBlack.copy(alpha = 0.9f))
                .clickable(onClick = onClick)
                .semantics { contentDescription = if (isSaved) "Remove from library" else "Save to library" },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = if (isSaved) SunflowerYellow else GalleryWhite,
            modifier = Modifier.size(size / 2),
        )
    }
}

private fun artworkTitle(art: ArtImage): String = art.tags.take(2).joinToString(" ") { it.trim().uppercase() }

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtworkPlaque(
    art: ArtImage,
    modifier: Modifier = Modifier,
    background: androidx.compose.ui.graphics.Color = InkBlack,
    content: androidx.compose.ui.graphics.Color = GalleryWhite,
) {
    Column(
        modifier =
            modifier
                .graphicsLayer { rotationZ = 1f }
                .drawHardOffsetShadow(4.dp, SunflowerYellow)
                .background(background)
                .padding(12.dp),
    ) {
        Text(
            text = artworkTitle(art),
            style = MaterialTheme.typography.headlineLarge,
            color = content,
        )
        Text(
            text = "@${art.userName}",
            style = MaterialTheme.typography.bodySmall,
            color = content.copy(alpha = 0.75f),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        ) {
            Text(
                text = "♥ ${art.likes}",
                style = MaterialTheme.typography.labelSmall,
                color = SunflowerYellow,
            )
            Text(
                text = "⬇ ${art.downloads}",
                style = MaterialTheme.typography.labelSmall,
                color = content,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun CompactArtworkPlaque(
    art: ArtImage,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .background(PaperCream)
                .padding(6.dp),
    ) {
        Text(
            text = artworkTitle(art),
            style = MaterialTheme.typography.headlineLarge,
            color = InkBlack,
            maxLines = 1,
        )
        Text(
            text = "@${art.userName}",
            style = MaterialTheme.typography.bodySmall,
            color = Graphite,
            maxLines = 1,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun DiscoveryDividerBand() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier =
            Modifier
                .fillMaxWidth()
                .graphicsLayer { rotationZ = -3f }
                .background(SignalRed)
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 6.dp, horizontal = 12.dp),
    ) {
        listOf("CURATOR'S RADICAL PICK • ISSUE #42", "DO NOT DUPLICATE", "ARTSY ARCHIVE").forEach { label ->
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = GalleryWhite,
            )
        }
    }
}

