package com.tylerdev.artshelf.presentation.screens.library.state

import androidx.compose.runtime.Immutable

@Immutable
sealed class LibraryUiState {
    data object Loading : LibraryUiState()
    data object Empty : LibraryUiState()
    data object Content : LibraryUiState()
}
