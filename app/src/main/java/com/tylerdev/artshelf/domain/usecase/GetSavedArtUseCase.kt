package com.tylerdev.artshelf.domain.usecase

import androidx.paging.PagingData
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedArtUseCase @Inject constructor(
    private val savedArtRepository: SavedArtRepository,
) {
    operator fun invoke(): Flow<PagingData<ArtImage>> = savedArtRepository.getSavedArt()
}
