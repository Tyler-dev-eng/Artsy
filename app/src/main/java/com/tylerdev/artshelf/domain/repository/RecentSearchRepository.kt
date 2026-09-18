package com.tylerdev.artshelf.domain.repository

import com.tylerdev.artshelf.domain.model.RecentSearch
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {
    fun getRecentSearches(limit: Int): Flow<List<RecentSearch>>
    suspend fun recordSearch(term: String)
    suspend fun removeSearch(term: String)
    suspend fun clearSearches()
}
