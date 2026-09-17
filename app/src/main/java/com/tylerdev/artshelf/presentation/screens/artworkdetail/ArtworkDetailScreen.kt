package com.tylerdev.artshelf.presentation.screens.artworkdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.presentation.components.drawHardOffsetShadow
import com.tylerdev.artshelf.presentation.screens.artworkdetail.state.ArtworkDetailUiState
import com.tylerdev.artshelf.presentation.screens.artworkdetail.viewmodel.ArtworkDetailViewModel
import com.tylerdev.artshelf.presentation.ui.theme.Background
import com.tylerdev.artshelf.presentation.ui.theme.GalleryWhite
import com.tylerdev.artshelf.presentation.ui.theme.InkBlack
import com.tylerdev.artshelf.presentation.ui.theme.PaperCream
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed
import com.tylerdev.artshelf.presentation.ui.theme.SunflowerYellow
import com.tylerdev.artshelf.presentation.ui.theme.SurfaceContainerHigh

private val SCREEN_MARGIN = 20.dp
private val SECTION_GAP = 20.dp
private val TOUCH_TARGET = 48.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun ArtworkDetailScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArtworkDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { ArtworkDetailTopBar(onBackClick = onBackClick) },
    ) { innerPadding ->
        when (val state = uiState) {
            is ArtworkDetailUiState.Content -> {
                ArtworkDetailContent(
                    art = state.art,
                    isSaved = state.isSaved,
                    onSaveClick = viewModel::onSaveClick,
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtworkDetailTopBar(onBackClick: () -> Unit) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(Background)
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(TOUCH_TARGET)
                    .drawHardOffsetShadow(2.dp, SignalRed)
                    .background(InkBlack)
                    .clickable(onClick = onBackClick)
                    .semantics { contentDescription = "Back" },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                tint = GalleryWhite,
            )
        }
        Text(
            text = "ARTWORK",
            style = MaterialTheme.typography.headlineLarge,
            color = GalleryWhite,
            modifier = Modifier.padding(start = 12.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtworkDetailContent(
    art: ArtImage,
    isSaved: Boolean,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = SCREEN_MARGIN, vertical = SECTION_GAP),
        verticalArrangement = Arrangement.spacedBy(SECTION_GAP),
    ) {
        ArtworkHeroImage(art = art, isSaved = isSaved, onSaveClick = onSaveClick)
        ArtworkInfoPanel(art = art)
        if (art.tags.isNotEmpty()) {
            ArtworkTagRow(tags = art.tags)
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtworkHeroImage(
    art: ArtImage,
    isSaved: Boolean,
    onSaveClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .graphicsLayer { rotationZ = -1f }
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
            AsyncImage(
                model =
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(art.largeImageUrl)
                        .crossfade(true)
                        .build(),
                contentDescription = art.tags.firstOrNull(),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            ArtworkSaveButton(
                isSaved = isSaved,
                onClick = onSaveClick,
                modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtworkSaveButton(
    isSaved: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(TOUCH_TARGET)
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
            modifier = Modifier.size(24.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtworkInfoPanel(art: ArtImage) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .graphicsLayer { rotationZ = 1f }
                .drawHardOffsetShadow(4.dp, SunflowerYellow)
                .background(InkBlack)
                .padding(16.dp),
    ) {
        Text(
            text = art.tags.take(2).joinToString(" ") { it.trim().uppercase() },
            style = MaterialTheme.typography.headlineLarge,
            color = GalleryWhite,
        )
        Text(
            text = "@${art.userName}",
            style = MaterialTheme.typography.bodySmall,
            color = GalleryWhite.copy(alpha = 0.75f),
            modifier = Modifier.padding(top = 4.dp),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
        ) {
            Text(
                text = "♥ ${art.likes} LIKES",
                style = MaterialTheme.typography.labelSmall,
                color = SunflowerYellow,
            )
            Text(
                text = "⬇ ${art.downloads} DOWNLOADS",
                style = MaterialTheme.typography.labelSmall,
                color = GalleryWhite,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtworkTagRow(tags: List<String>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        itemsIndexed(items = tags, key = { index, tag -> "$index-$tag" }) { _, tag ->
            Text(
                text = tag.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = InkBlack,
                modifier =
                    Modifier
                        .drawHardOffsetShadow(2.dp, InkBlack)
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
            )
        }
    }
}
