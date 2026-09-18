package com.tylerdev.artshelf.presentation.screens.library

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.presentation.components.ArtShelfTopBar
import com.tylerdev.artshelf.presentation.components.drawHardOffsetShadow
import com.tylerdev.artshelf.presentation.screens.library.state.LibraryUiState
import com.tylerdev.artshelf.presentation.screens.library.viewmodel.LibraryViewModel
import com.tylerdev.artshelf.presentation.ui.theme.GalleryWhite
import com.tylerdev.artshelf.presentation.ui.theme.InkBlack
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed

private val UPLOAD_FAB_SIZE = 56.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    onExploreClick: () -> Unit = {},
    onArtClick: (ArtImage) -> Unit = {},
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri -> uri?.let { viewModel.onImagePicked(it.toString()) } }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { ArtShelfTopBar(title = "LIBRARY") },
        floatingActionButton = {
            UploadFromDeviceFab(
                onClick = {
                    pickImageLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                },
            )
        },
    ) { innerPadding ->
        when (uiState) {
            LibraryUiState.Loading -> {
                Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = SignalRed)
                }
            }

            LibraryUiState.Empty -> {
                LibraryEmptyState(modifier = Modifier.padding(innerPadding), onExploreClick = onExploreClick)
            }

            LibraryUiState.Content -> {
                val savedArtItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
                LibraryScreenResults(
                    artItems = savedArtItems,
                    modifier = Modifier.padding(innerPadding),
                    onRemoveClick = viewModel::onRemoveClick,
                    onArtClick = onArtClick,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun UploadFromDeviceFab(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(UPLOAD_FAB_SIZE)
            .drawHardOffsetShadow(4.dp, InkBlack)
            .background(SignalRed)
            .clickable(onClick = onClick)
            .semantics { contentDescription = "Upload artwork from device" },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            tint = GalleryWhite,
        )
    }
}
