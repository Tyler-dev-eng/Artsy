package com.tylerdev.artshelf.domain.repository

import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ArtRepository {
    fun searchArt(query: String): Flow<Resource<List<ArtImage>>>
}
