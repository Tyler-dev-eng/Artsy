package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HasSavedArtUseCase @Inject constructor(
    private val savedArtRepository: SavedArtRepository,
) {
    operator fun invoke(): Flow<Boolean> = savedArtRepository.hasSavedArt()
}
