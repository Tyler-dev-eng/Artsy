package com.tylerdev.artshelf.presentation.screens.library

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.tylerdev.artshelf.presentation.components.ArtShelfTopBar
import com.tylerdev.artshelf.presentation.screens.library.state.LibraryUiState
import com.tylerdev.artshelf.presentation.screens.library.viewmodel.LibraryViewModel
import com.tylerdev.artshelf.presentation.ui.theme.SignalRed

@Suppress("ktlint:standard:function-naming")
@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    onExploreClick: () -> Unit = {},
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { ArtShelfTopBar(title = "LIBRARY") },
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
                )
            }
        }
    }
}
