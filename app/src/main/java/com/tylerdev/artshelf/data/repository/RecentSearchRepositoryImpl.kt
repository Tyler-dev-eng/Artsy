package com.tylerdev.artshelf.data.repository

import com.tylerdev.artshelf.data.local.dao.RecentSearchDao
import com.tylerdev.artshelf.data.local.entity.RecentSearchEntity
import com.tylerdev.artshelf.data.local.entity.toRecentSearch
import com.tylerdev.artshelf.domain.model.RecentSearch
import com.tylerdev.artshelf.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentSearchRepositoryImpl
    @Inject
    constructor(
        private val recentSearchDao: RecentSearchDao,
    ) : RecentSearchRepository {

        override fun getRecentSearches(limit: Int): Flow<List<RecentSearch>> =
            recentSearchDao.getRecent(limit).map { entities -> entities.map { it.toRecentSearch() } }

        override suspend fun recordSearch(term: String) {
            recentSearchDao.upsert(
                RecentSearchEntity(term = term, searchedAtEpochMillis = System.currentTimeMillis()),
            )
        }

        override suspend fun removeSearch(term: String) {
            recentSearchDao.deleteByTerm(term)
        }

        override suspend fun clearSearches() {
            recentSearchDao.deleteAll()
        }
    }
