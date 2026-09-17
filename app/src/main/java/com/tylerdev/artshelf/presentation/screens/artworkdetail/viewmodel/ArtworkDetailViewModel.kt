package com.tylerdev.artshelf.presentation.screens.artworkdetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.usecase.IsArtSavedUseCase
import com.tylerdev.artshelf.domain.usecase.ToggleSaveArtUseCase
import com.tylerdev.artshelf.presentation.screens.artworkdetail.state.ArtworkDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val UI_STATE_STOP_TIMEOUT_MILLIS = 5_000L
private const val ARTWORK_TAGS_DELIMITER = ","

private fun SavedStateHandle.toArtImage(): ArtImage =
    ArtImage(
        id = checkNotNull(get<String>("id")).toLong(),
        pageUrl = checkNotNull(get<String>("pageUrl")),
        previewUrl = checkNotNull(get<String>("previewUrl")),
        webformatUrl = checkNotNull(get<String>("webformatUrl")),
        largeImageUrl = checkNotNull(get<String>("largeImageUrl")),
        tags = checkNotNull(get<String>("tags")).split(ARTWORK_TAGS_DELIMITER).map { it.trim() },
        userName = checkNotNull(get<String>("userName")),
        likes = checkNotNull(get<String>("likes")).toInt(),
        downloads = checkNotNull(get<String>("downloads")).toInt(),
    )

@HiltViewModel
class ArtworkDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val toggleSaveArtUseCase: ToggleSaveArtUseCase,
        isArtSavedUseCase: IsArtSavedUseCase,
    ) : ViewModel() {
        private val art: ArtImage = savedStateHandle.toArtImage()

        val uiState: StateFlow<ArtworkDetailUiState> =
            isArtSavedUseCase(art.id)
                .map { isSaved -> ArtworkDetailUiState.Content(art = art, isSaved = isSaved) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(UI_STATE_STOP_TIMEOUT_MILLIS),
                    initialValue = ArtworkDetailUiState.Content(art = art, isSaved = false),
                )

        fun onSaveClick() {
            viewModelScope.launch { toggleSaveArtUseCase(art) }
        }
    }
