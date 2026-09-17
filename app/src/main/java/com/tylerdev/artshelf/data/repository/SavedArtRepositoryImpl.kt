package com.tylerdev.artshelf.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tylerdev.artshelf.data.local.dao.SavedArtDao
import com.tylerdev.artshelf.data.local.entity.toArtImage
import com.tylerdev.artshelf.data.local.entity.toSavedArtEntity
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PAGE_SIZE = 20

class SavedArtRepositoryImpl
    @Inject
    constructor(
        private val savedArtDao: SavedArtDao,
    ) : SavedArtRepository {

        override fun getSavedArt(): Flow<PagingData<ArtImage>> =
            Pager(
                config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
                pagingSourceFactory = { savedArtDao.pagingSource() },
            ).flow.map { pagingData -> pagingData.map { it.toArtImage() } }

        override fun isArtSaved(id: Long): Flow<Boolean> = savedArtDao.isSaved(id)

        override suspend fun saveArt(artImage: ArtImage) {
            savedArtDao.upsert(artImage.toSavedArtEntity(savedAtEpochMillis = System.currentTimeMillis()))
        }

        override suspend fun removeArt(id: Long) {
            savedArtDao.deleteById(id)
        }
    }
