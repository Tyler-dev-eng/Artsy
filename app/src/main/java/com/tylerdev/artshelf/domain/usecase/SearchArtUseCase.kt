package com.tylerdev.artshelf.domain.usecase

import androidx.paging.PagingData
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.repository.ArtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchArtUseCase @Inject constructor(
    private val artRepository: ArtRepository,
) {
    operator fun invoke(query: String): Flow<PagingData<ArtImage>> =
        artRepository.searchArt(query)
}
