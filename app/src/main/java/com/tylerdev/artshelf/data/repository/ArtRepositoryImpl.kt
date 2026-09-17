package com.tylerdev.artshelf.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tylerdev.artshelf.data.remote.ArtApi
import com.tylerdev.artshelf.data.remote.paging.ArtSearchPagingSource
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.repository.ArtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 20

class ArtRepositoryImpl
    @Inject
    constructor(
        private val artApi: ArtApi,
    ) : ArtRepository {
        override fun searchArt(query: String): Flow<PagingData<ArtImage>> =
            Pager(
                config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
                pagingSourceFactory = { ArtSearchPagingSource(artApi, query) },
            ).flow
    }
