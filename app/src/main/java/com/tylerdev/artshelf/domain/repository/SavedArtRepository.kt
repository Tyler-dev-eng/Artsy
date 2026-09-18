package com.tylerdev.artshelf.domain.repository

import androidx.paging.PagingData
import com.tylerdev.artshelf.domain.model.ArtImage
import kotlinx.coroutines.flow.Flow

interface SavedArtRepository {
    fun getSavedArt(): Flow<PagingData<ArtImage>>
    fun isArtSaved(id: Long): Flow<Boolean>
    fun hasSavedArt(): Flow<Boolean>
    fun getSavedArtIds(): Flow<Set<Long>>
    fun getArtById(id: Long): Flow<ArtImage?>
    suspend fun saveArt(artImage: ArtImage)
    suspend fun saveUploadedArt(sourceUriString: String): ArtImage
    suspend fun removeArt(id: Long)
    suspend fun updateArtNotes(id: Long, notes: String?)
    suspend fun updateArtDetails(id: Long, title: String?, userName: String)
}
