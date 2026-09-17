package com.tylerdev.artshelf.domain.repository

import androidx.paging.PagingData
import com.tylerdev.artshelf.domain.model.ArtImage
import kotlinx.coroutines.flow.Flow

interface ArtRepository {
    fun searchArt(query: String): Flow<PagingData<ArtImage>>
}
