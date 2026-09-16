package com.tylerdev.artshelf.presentation.screens.search.state

import androidx.compose.runtime.Immutable
import com.tylerdev.artshelf.domain.model.ArtImage

@Immutable
sealed class SearchUiState {
    data object Idle : SearchUiState()
    data object Loading : SearchUiState()

    @Immutable
    data class Success(val artImages: List<ArtImage>) : SearchUiState()

    @Immutable
    data class Error(val message: String) : SearchUiState()
}
