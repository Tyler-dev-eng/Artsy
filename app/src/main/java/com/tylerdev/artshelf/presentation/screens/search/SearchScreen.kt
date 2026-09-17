package com.tylerdev.artshelf.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tylerdev.artshelf.presentation.components.ArtShelfTopBar
import com.tylerdev.artshelf.presentation.components.ArtsySearchBar
import com.tylerdev.artshelf.presentation.screens.search.state.SearchUiState
import com.tylerdev.artshelf.presentation.screens.search.viewmodel.SearchViewModel

private val SCREEN_HORIZONTAL_MARGIN = 20.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { ArtShelfTopBar(title = "SEARCH") },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ArtsySearchBar(
                query = query,
                onQueryChange = viewModel::onQueryChanged,
                modifier = Modifier.padding(horizontal = SCREEN_HORIZONTAL_MARGIN),
            )

            when (uiState) {
                is SearchUiState.Idle -> {
                    SearchScreenInitialState(
                        onSeedClick = viewModel::onQueryChanged,
                        onFeaturedSeedClick = viewModel::onQueryChanged,
                        onSignalClick = viewModel::onQueryChanged,
                    )
                }

                is SearchUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is SearchUiState.Empty -> {
                    SearchScreenEmptyState(
                        onTagClick = viewModel::onQueryChanged,
                        onClearAndExploreClick = { viewModel.onQueryChanged("") },
                    )
                }

                is SearchUiState.Error -> {
                    Text(text = (uiState as SearchUiState.Error).message)
                }

                is SearchUiState.Success -> {
                    val artImages = (uiState as SearchUiState.Success).artImages
                    LazyColumn {
                        items(artImages) { artImage ->
                            Text(text = artImage.tags.joinToString())
                        }
                    }
                }
            }
        }
    }
}
