package com.tylerdev.artshelf.domain.repository

import androidx.paging.PagingData
import com.tylerdev.artshelf.domain.model.ArtImage
import kotlinx.coroutines.flow.Flow

interface SavedArtRepository {
    fun getSavedArt(): Flow<PagingData<ArtImage>>
    fun isArtSaved(id: Long): Flow<Boolean>
    fun hasSavedArt(): Flow<Boolean>
    fun getSavedArtIds(): Flow<Set<Long>>
    suspend fun saveArt(artImage: ArtImage)
    suspend fun removeArt(id: Long)
}
