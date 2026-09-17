package com.tylerdev.artshelf.presentation.screens.artworkdetail.state

import com.tylerdev.artshelf.domain.model.ArtImage

sealed class ArtworkDetailUiState {
    data class Content(
        val art: ArtImage,
        val isSaved: Boolean,
    ) : ArtworkDetailUiState()
}
