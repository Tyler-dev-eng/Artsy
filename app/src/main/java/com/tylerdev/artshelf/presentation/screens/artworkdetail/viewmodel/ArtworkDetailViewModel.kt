package com.tylerdev.artshelf.presentation.screens.artworkdetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.usecase.GetSavedArtByIdUseCase
import com.tylerdev.artshelf.domain.usecase.IsArtSavedUseCase
import com.tylerdev.artshelf.domain.usecase.ToggleSaveArtUseCase
import com.tylerdev.artshelf.domain.usecase.UpdateArtDetailsUseCase
import com.tylerdev.artshelf.domain.usecase.UpdateArtNotesUseCase
import com.tylerdev.artshelf.presentation.screens.artworkdetail.state.ArtworkDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
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
        private val updateArtNotesUseCase: UpdateArtNotesUseCase,
        private val updateArtDetailsUseCase: UpdateArtDetailsUseCase,
        isArtSavedUseCase: IsArtSavedUseCase,
        getSavedArtByIdUseCase: GetSavedArtByIdUseCase,
    ) : ViewModel() {
        private val navArt: ArtImage = savedStateHandle.toArtImage()

        private val _isEditDialogVisible = MutableStateFlow(false)
        val isEditDialogVisible: StateFlow<Boolean> = _isEditDialogVisible.asStateFlow()

        private val _isEditDetailsDialogVisible = MutableStateFlow(false)
        val isEditDetailsDialogVisible: StateFlow<Boolean> = _isEditDetailsDialogVisible.asStateFlow()

        val uiState: StateFlow<ArtworkDetailUiState> =
            isArtSavedUseCase(navArt.id)
                .flatMapLatest { isSaved ->
                    if (isSaved) {
                        getSavedArtByIdUseCase(navArt.id).map { savedArt ->
                            ArtworkDetailUiState.Content(art = savedArt ?: navArt, isSaved = true)
                        }
                    } else {
                        flowOf(ArtworkDetailUiState.Content(art = navArt, isSaved = false))
                    }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(UI_STATE_STOP_TIMEOUT_MILLIS),
                    initialValue = ArtworkDetailUiState.Content(art = navArt, isSaved = false),
                )

        fun onSaveClick() {
            viewModelScope.launch { toggleSaveArtUseCase(navArt) }
        }

        fun onEditClick() {
            _isEditDialogVisible.value = true
        }

        fun onDismissEditDialog() {
            _isEditDialogVisible.value = false
        }

        fun onSaveNotes(notes: String) {
            viewModelScope.launch {
                updateArtNotesUseCase(navArt.id, notes)
                _isEditDialogVisible.value = false
            }
        }

        fun onEditDetailsClick() {
            _isEditDetailsDialogVisible.value = true
        }

        fun onDismissEditDetailsDialog() {
            _isEditDetailsDialogVisible.value = false
        }

        fun onSaveDetails(title: String, userName: String) {
            viewModelScope.launch {
                updateArtDetailsUseCase(navArt.id, title, userName)
                _isEditDetailsDialogVisible.value = false
            }
        }
    }
