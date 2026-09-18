package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedArtByIdUseCase @Inject constructor(
    private val savedArtRepository: SavedArtRepository,
) {
    operator fun invoke(id: Long): Flow<ArtImage?> = savedArtRepository.getArtById(id)
}
