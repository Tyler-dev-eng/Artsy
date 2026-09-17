package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleSaveArtUseCase @Inject constructor(
    private val savedArtRepository: SavedArtRepository,
) {
    suspend operator fun invoke(artImage: ArtImage) {
        val isSaved = savedArtRepository.isArtSaved(artImage.id).first()
        if (isSaved) {
            savedArtRepository.removeArt(artImage.id)
        } else {
            savedArtRepository.saveArt(artImage)
        }
    }
}
