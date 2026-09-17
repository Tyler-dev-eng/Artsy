package com.tylerdev.artshelf.presentation.screens.search.state

import androidx.compose.runtime.Immutable

@Immutable
sealed class SearchUiState {
    data object Idle : SearchUiState()
    data object Active : SearchUiState()
}
