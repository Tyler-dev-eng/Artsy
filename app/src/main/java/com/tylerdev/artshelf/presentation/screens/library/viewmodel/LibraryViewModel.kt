package com.tylerdev.artshelf.presentation.screens.library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.usecase.GetSavedArtUseCase
import com.tylerdev.artshelf.domain.usecase.HasSavedArtUseCase
import com.tylerdev.artshelf.domain.usecase.ToggleSaveArtUseCase
import com.tylerdev.artshelf.domain.usecase.UploadArtUseCase
import com.tylerdev.artshelf.presentation.screens.library.state.LibraryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val UI_STATE_STOP_TIMEOUT_MILLIS = 5_000L

@HiltViewModel
class LibraryViewModel
    @Inject
    constructor(
        getSavedArtUseCase: GetSavedArtUseCase,
        hasSavedArtUseCase: HasSavedArtUseCase,
        private val toggleSaveArtUseCase: ToggleSaveArtUseCase,
        private val uploadArtUseCase: UploadArtUseCase,
    ) : ViewModel() {

        val uiState: StateFlow<LibraryUiState> =
            hasSavedArtUseCase()
                .map { hasSavedArt -> if (hasSavedArt) LibraryUiState.Content else LibraryUiState.Empty }
                .distinctUntilChanged()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(UI_STATE_STOP_TIMEOUT_MILLIS),
                    initialValue = LibraryUiState.Loading,
                )

        val pagingDataFlow: Flow<PagingData<ArtImage>> =
            getSavedArtUseCase().cachedIn(viewModelScope)

        fun onRemoveClick(artImage: ArtImage) {
            viewModelScope.launch { toggleSaveArtUseCase(artImage) }
        }

        fun onImagePicked(sourceUriString: String) {
            viewModelScope.launch { runCatching { uploadArtUseCase(sourceUriString) } }
        }
    }
