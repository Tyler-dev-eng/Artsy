package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import javax.inject.Inject

class UploadArtUseCase @Inject constructor(
    private val savedArtRepository: SavedArtRepository,
) {
    suspend operator fun invoke(sourceUriString: String): ArtImage =
        savedArtRepository.saveUploadedArt(sourceUriString)
}
