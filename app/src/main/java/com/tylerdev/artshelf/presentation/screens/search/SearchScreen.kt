package com.tylerdev.artshelf.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tylerdev.artshelf.presentation.screens.search.state.SearchUiState
import com.tylerdev.artshelf.presentation.screens.search.viewmodel.SearchViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            OutlinedTextField(
                value = query,
                onValueChange = viewModel::onQueryChanged,
                label = { Text("Search art") },
            )

            when (uiState) {
                is SearchUiState.Idle -> Unit
                is SearchUiState.Loading -> CircularProgressIndicator()
                is SearchUiState.Error -> Text(text = (uiState as SearchUiState.Error).message)
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
