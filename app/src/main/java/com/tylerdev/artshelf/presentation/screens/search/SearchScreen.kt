package com.tylerdev.artshelf.presentation.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.presentation.components.ArtShelfTopBar
import com.tylerdev.artshelf.presentation.components.ArtsySearchBar
import com.tylerdev.artshelf.presentation.screens.search.state.SearchUiState
import com.tylerdev.artshelf.presentation.screens.search.viewmodel.SearchViewModel
import retrofit2.HttpException
import java.io.IOException

private val SCREEN_HORIZONTAL_MARGIN = 20.dp
private const val NETWORK_ERROR_MESSAGE = "Couldn't reach Pixabay. Check your connection."
private const val SERVER_ERROR_MESSAGE = "Something went wrong on the server."
private const val GENERIC_ERROR_MESSAGE = "Something went wrong."

@Suppress("ktlint:standard:function-naming")
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onArtClick: (ArtImage) -> Unit = {},
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val savedArtIds by viewModel.savedArtIds.collectAsStateWithLifecycle()
    val recentSearches by viewModel.recentSearches.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { ArtShelfTopBar(title = "SEARCH") },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            ArtsySearchBar(
                query = query,
                onQueryChange = viewModel::onQueryChanged,
                onSearchSubmit = viewModel::onSearchSubmitted,
                modifier = Modifier.padding(horizontal = SCREEN_HORIZONTAL_MARGIN),
            )

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                when (uiState) {
                    is SearchUiState.Idle -> {
                        SearchScreenInitialState(
                            onSeedClick = viewModel::onQueryCommitted,
                            recentSearches = recentSearches,
                            onSignalClick = viewModel::onQueryCommitted,
                            onRemoveSignal = viewModel::onRemoveRecentSearch,
                            onClearAllSignals = viewModel::onClearRecentSearches,
                        )
                    }

                    is SearchUiState.Active -> {
                        val artItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
                        SearchScreenActiveContent(
                            query = query,
                            artItems = artItems,
                            savedArtIds = savedArtIds,
                            onSaveClick = viewModel::onSaveClick,
                            onArtClick = onArtClick,
                            onEmptyTagClick = viewModel::onQueryCommitted,
                            onEmptyClearAndExploreClick = { viewModel.onQueryChanged("") },
                        )
                    }
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun BoxScope.SearchScreenActiveContent(
    query: String,
    artItems: LazyPagingItems<ArtImage>,
    savedArtIds: Set<Long>,
    onSaveClick: (ArtImage) -> Unit,
    onArtClick: (ArtImage) -> Unit,
    onEmptyTagClick: (String) -> Unit,
    onEmptyClearAndExploreClick: () -> Unit,
) {
    when (val refreshState = artItems.loadState.refresh) {
        is LoadState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        is LoadState.Error -> {
            Text(
                text = refreshState.error.toUserMessage(),
                modifier = Modifier.align(Alignment.Center),
            )
        }

        is LoadState.NotLoading -> {
            if (artItems.itemCount == 0) {
                SearchScreenEmptyState(
                    onTagClick = onEmptyTagClick,
                    onClearAndExploreClick = onEmptyClearAndExploreClick,
                )
            } else {
                SearchScreenResults(
                    query = query,
                    artItems = artItems,
                    savedArtIds = savedArtIds,
                    onSaveClick = onSaveClick,
                    onArtClick = onArtClick,
                )
            }
        }
    }
}

private fun Throwable.toUserMessage(): String =
    when (this) {
        is IOException -> NETWORK_ERROR_MESSAGE
        is HttpException -> SERVER_ERROR_MESSAGE
        else -> GENERIC_ERROR_MESSAGE
    }
