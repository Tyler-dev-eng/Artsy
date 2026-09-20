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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.skydoves.navgraph.annotations.NavDestination
import com.github.skydoves.navgraph.annotations.NavPreview
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.model.displayTitle
import com.tylerdev.artshelf.presentation.components.drawHardOffsetShadow
import com.tylerdev.artshelf.presentation.navigation.Screen
import com.tylerdev.artshelf.presentation.preview.PREVIEW_ART_IMAGES
import com.tylerdev.artshelf.presentation.screens.artworkdetail.state.ArtworkDetailUiState
import com.tylerdev.artshelf.presentation.screens.artworkdetail.viewmodel.ArtworkDetailViewModel
import com.tylerdev.artshelf.presentation.ui.theme.ArtShelfTheme
import com.tylerdev.artshelf.presentation.ui.theme.Background
import com.tylerdev.artshelf.presentation.ui.theme.GalleryWhite
import com.tylerdev.artshelf.presentation.ui.theme.Graphite
import com.tylerdev.artshelf.presentation.ui.theme.InkBlack
import com.tylerdev.artshelf.presentation.ui.theme.PaperCream
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed
import com.tylerdev.artshelf.presentation.ui.theme.SunflowerYellow
import com.tylerdev.artshelf.presentation.ui.theme.SurfaceContainerHigh

private val SCREEN_MARGIN = 20.dp
private val SECTION_GAP = 20.dp
private val TOUCH_TARGET = 48.dp
private val NOTES_INPUT_MIN_HEIGHT = 120.dp
private val DIALOG_SHADOW_OFFSET = 6.dp
private val DIALOG_PADDING = 20.dp

@Suppress("ktlint:standard:function-naming")
@NavDestination(route = Screen.ArtworkDetail::class)
@Composable
fun ArtworkDetailScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArtworkDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isEditDialogVisible by viewModel.isEditDialogVisible.collectAsStateWithLifecycle()
    val isEditDetailsDialogVisible by viewModel.isEditDetailsDialogVisible.collectAsStateWithLifecycle()

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
                    onEditClick = viewModel::onEditClick,
                    onEditDetailsClick = viewModel::onEditDetailsClick,
                    modifier = Modifier.padding(innerPadding),
                )
                if (isEditDialogVisible) {
                    EditNotesDialog(
                        initialNotes = state.art.notes.orEmpty(),
                        onDismiss = viewModel::onDismissEditDialog,
                        onSave = viewModel::onSaveNotes,
                    )
                }
                if (isEditDetailsDialogVisible) {
                    EditDetailsDialog(
                        initialTitle = state.art.displayTitle(),
                        initialUserName = state.art.userName,
                        onDismiss = viewModel::onDismissEditDetailsDialog,
                        onSave = viewModel::onSaveDetails,
                    )
                }
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
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
    onEditClick: () -> Unit,
    onEditDetailsClick: () -> Unit,
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
        ArtworkInfoPanel(art = art, isSaved = isSaved, onEditDetailsClick = onEditDetailsClick)
        if (art.tags.isNotEmpty()) {
            ArtworkTagRow(tags = art.tags)
        }
        if (isSaved) {
            ArtworkNotesPanel(notes = art.notes, onEditClick = onEditClick)
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
private fun ArtworkInfoPanel(
    art: ArtImage,
    isSaved: Boolean,
    onEditDetailsClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .graphicsLayer { rotationZ = 1f }
                .drawHardOffsetShadow(4.dp, SunflowerYellow)
                .background(InkBlack)
                .padding(16.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = art.displayTitle(),
                style = MaterialTheme.typography.headlineLarge,
                color = GalleryWhite,
            )
            if (isSaved) {
                ArtworkEditButton(onClick = onEditDetailsClick)
            }
        }
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

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtworkNotesPanel(
    notes: String?,
    onEditClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .drawHardOffsetShadow(4.dp, InkBlack)
                .background(PaperCream)
                .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "NOTES",
                style = MaterialTheme.typography.labelSmall,
                color = InkBlack,
            )
            ArtworkEditButton(onClick = onEditClick)
        }
        Text(
            text = notes?.takeIf { it.isNotBlank() } ?: "Add your own notes about this piece…",
            style = MaterialTheme.typography.bodyMedium,
            color = if (notes.isNullOrBlank()) Graphite else InkBlack,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtworkEditButton(onClick: () -> Unit) {
    Box(
        modifier =
            Modifier
                .size(32.dp)
                .drawHardOffsetShadow(2.dp, InkBlack)
                .background(SunflowerYellow)
                .clickable(onClick = onClick)
                .semantics { contentDescription = "Edit notes" },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = null,
            tint = InkBlack,
            modifier = Modifier.size(18.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun EditNotesDialog(
    initialNotes: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
) {
    var notes by remember { mutableStateOf(initialNotes) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(SCREEN_MARGIN)
                    .drawHardOffsetShadow(DIALOG_SHADOW_OFFSET, InkBlack)
                    .background(PaperCream)
                    .padding(DIALOG_PADDING),
        ) {
            Text(
                text = "EDIT NOTES",
                style = MaterialTheme.typography.headlineLarge,
                color = InkBlack,
            )
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .heightIn(min = NOTES_INPUT_MIN_HEIGHT)
                        .drawHardOffsetShadow(2.dp, InkBlack)
                        .background(GalleryWhite)
                        .padding(12.dp),
            ) {
                if (notes.isEmpty()) {
                    Text(
                        text = "Add your own notes about this piece…",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Graphite,
                    )
                }
                BasicTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    textStyle =
                        LocalTextStyle.current.merge(MaterialTheme.typography.bodyMedium).copy(color = InkBlack),
                    cursorBrush = SolidColor(SignalRed),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                DialogTextButton(text = "CANCEL", color = SurfaceContainerHigh, onClick = onDismiss)
                DialogTextButton(
                    text = "SAVE",
                    color = SignalRed,
                    textColor = GalleryWhite,
                    onClick = { onSave(notes) },
                    modifier = Modifier.padding(start = 12.dp),
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun EditDetailsDialog(
    initialTitle: String,
    initialUserName: String,
    onDismiss: () -> Unit,
    onSave: (title: String, userName: String) -> Unit,
) {
    var title by remember { mutableStateOf(initialTitle) }
    var userName by remember { mutableStateOf(initialUserName) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(SCREEN_MARGIN)
                    .drawHardOffsetShadow(DIALOG_SHADOW_OFFSET, InkBlack)
                    .background(PaperCream)
                    .padding(DIALOG_PADDING),
        ) {
            Text(
                text = "EDIT DETAILS",
                style = MaterialTheme.typography.headlineLarge,
                color = InkBlack,
            )
            DetailsInputField(
                label = "TITLE",
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.padding(top = 12.dp),
            )
            DetailsInputField(
                label = "ARTIST NAME",
                value = userName,
                onValueChange = { userName = it },
                modifier = Modifier.padding(top = 12.dp),
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                DialogTextButton(text = "CANCEL", color = SurfaceContainerHigh, onClick = onDismiss)
                DialogTextButton(
                    text = "SAVE",
                    color = SignalRed,
                    textColor = GalleryWhite,
                    onClick = { onSave(title, userName) },
                    modifier = Modifier.padding(start = 12.dp),
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun DetailsInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Graphite,
        )
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .drawHardOffsetShadow(2.dp, InkBlack)
                    .background(GalleryWhite)
                    .padding(12.dp),
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle =
                    LocalTextStyle.current.merge(MaterialTheme.typography.bodyMedium).copy(color = InkBlack),
                cursorBrush = SolidColor(SignalRed),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@NavPreview(route = Screen.ArtworkDetail::class, primary = true)
@Preview
@Suppress("ktlint:standard:function-naming")
@Composable
private fun ArtworkDetailScreenPreview() {
    ArtShelfTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { ArtworkDetailTopBar(onBackClick = {}) },
        ) { innerPadding ->
            ArtworkDetailContent(
                art = PREVIEW_ART_IMAGES.first(),
                isSaved = true,
                onSaveClick = {},
                onEditClick = {},
                onEditDetailsClick = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun DialogTextButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = InkBlack,
) {
    Box(
        modifier =
            modifier
                .drawHardOffsetShadow(2.dp, InkBlack)
                .background(color)
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
        )
    }
}
