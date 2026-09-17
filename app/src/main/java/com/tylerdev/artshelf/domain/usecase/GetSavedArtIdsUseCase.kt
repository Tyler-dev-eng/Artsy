package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedArtIdsUseCase @Inject constructor(
    private val savedArtRepository: SavedArtRepository,
) {
    operator fun invoke(): Flow<Set<Long>> = savedArtRepository.getSavedArtIds()
}
