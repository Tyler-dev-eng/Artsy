package com.tylerdev.artshelf.presentation.screens.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.model.displayTitle
import com.tylerdev.artshelf.presentation.components.drawHardOffsetShadow
import com.tylerdev.artshelf.presentation.ui.theme.GalleryWhite
import com.tylerdev.artshelf.presentation.ui.theme.Graphite
import com.tylerdev.artshelf.presentation.ui.theme.InkBlack
import com.tylerdev.artshelf.presentation.ui.theme.PaperCream
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed
import com.tylerdev.artshelf.presentation.ui.theme.SunflowerYellow
import com.tylerdev.artshelf.presentation.ui.theme.SurfaceContainerHigh

private val SCREEN_MARGIN = 20.dp
private val SECTION_GAP = 24.dp
private val TOUCH_TARGET = 48.dp
private val NOTE_INDICATOR_SIZE = 28.dp

internal fun noteIndicatorTestTag(artId: Long): String = "note_indicator_$artId"
private val CARD_ROTATIONS = listOf(-1f, 3f, -3f)
private const val CATALOG_TAG_PREFIX = "#"

@Suppress("ktlint:standard:function-naming")
@Composable
fun LibraryScreenResults(
    artItems: LazyPagingItems<ArtImage>,
    modifier: Modifier = Modifier,
    onRemoveClick: (ArtImage) -> Unit = {},
    onArtClick: (ArtImage) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = SCREEN_MARGIN, vertical = SECTION_GAP),
        verticalArrangement = Arrangement.spacedBy(SECTION_GAP),
    ) {
        item(key = "collection-header") {
            LibraryHeaderBand(pieceCount = artItems.itemCount)
        }
        items(
            count = artItems.itemCount,
            key = { index -> artItems.peek(index)?.id ?: index },
            contentType = { "saved-art-card" },
        ) { index ->
            val art = artItems[index] ?: return@items
            SavedArtCard(
                art = art,
                rotationDegrees = CARD_ROTATIONS[index % CARD_ROTATIONS.size],
                onRemoveClick = { onRemoveClick(art) },
                onClick = { onArtClick(art) },
            )
        }
        item(key = "append-load-state") {
            SavedArtAppendFooter(loadState = artItems.loadState.append, onRetry = artItems::retry)
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun LibraryHeaderBand(pieceCount: Int) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(PaperCream)
                .padding(20.dp),
        ) {
            Text(
                text = "YOUR COLLECTION",
                style = MaterialTheme.typography.headlineMedium,
                color = GalleryWhite,
                modifier = Modifier
                    .graphicsLayer { rotationZ = -4f }
                    .drawHardOffsetShadow(4.dp, SignalRed)
                    .background(InkBlack)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
            )
            Text(
                text = "$pieceCount PIECES CURATED",
                style = MaterialTheme.typography.labelSmall,
                color = SunflowerYellow,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .graphicsLayer { rotationZ = 2f }
                    .drawHardOffsetShadow(3.dp, InkBlack)
                    .background(SignalRed)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SavedArtCard(
    art: ArtImage,
    rotationDegrees: Float,
    onRemoveClick: () -> Unit,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { rotationZ = rotationDegrees },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .drawHardOffsetShadow(6.dp, InkBlack)
                .background(InkBlack)
                .clickable(onClick = onClick)
                .padding(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .background(SurfaceContainerHigh),
            ) {
                SavedArtworkImage(art = art, modifier = Modifier.fillMaxSize())
                CatalogTag(
                    id = art.id,
                    modifier = Modifier.align(Alignment.BottomStart).padding(8.dp),
                )
                UnsaveButton(
                    onClick = onRemoveClick,
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                )
                if (!art.notes.isNullOrBlank()) {
                    NoteIndicator(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .testTag(noteIndicatorTestTag(art.id)),
                    )
                }
            }
            SavedArtPlaque(art = art, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SavedArtworkImage(
    art: ArtImage,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
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
private fun CatalogTag(
    id: Long,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "$CATALOG_TAG_PREFIX$id",
        style = MaterialTheme.typography.labelSmall,
        color = SunflowerYellow,
        modifier = modifier
            .background(InkBlack.copy(alpha = 0.9f))
            .padding(horizontal = 8.dp, vertical = 2.dp),
    )
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun UnsaveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(TOUCH_TARGET)
            .drawHardOffsetShadow(2.dp, SignalRed)
            .background(InkBlack.copy(alpha = 0.9f))
            .clickable(onClick = onClick)
            .semantics { contentDescription = "Remove from library" },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = SunflowerYellow,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun NoteIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(NOTE_INDICATOR_SIZE)
            .drawHardOffsetShadow(2.dp, InkBlack)
            .background(SunflowerYellow)
            .semantics { contentDescription = "Has notes" },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = null,
            tint = InkBlack,
            modifier = Modifier.size(16.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SavedArtPlaque(
    art: ArtImage,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PaperCream)
            .padding(12.dp),
    ) {
        Text(
            text = art.displayTitle(),
            style = MaterialTheme.typography.headlineSmall,
            color = InkBlack,
            maxLines = 1,
        )
        Text(
            text = "@${art.userName}",
            style = MaterialTheme.typography.bodySmall,
            color = Graphite,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SavedArtAppendFooter(
    loadState: LoadState,
    onRetry: () -> Unit,
) {
    when (loadState) {
        is LoadState.Loading -> {
            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = SignalRed)
            }
        }

        is LoadState.Error -> {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .drawHardOffsetShadow(3.dp, InkBlack)
                    .background(SignalRed)
                    .clickable(onClick = onRetry)
                    .semantics { contentDescription = "Retry loading more saved art" }
                    .padding(vertical = 12.dp),
            ) {
                Icon(imageVector = Icons.Filled.Refresh, contentDescription = null, tint = GalleryWhite)
                Text(
                    text = "COULDN'T LOAD MORE — TAP TO RETRY",
                    style = MaterialTheme.typography.labelMedium,
                    color = GalleryWhite,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }

        is LoadState.NotLoading -> Unit
    }
}
