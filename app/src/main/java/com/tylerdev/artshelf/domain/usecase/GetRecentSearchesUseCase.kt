package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.model.RecentSearch
import com.tylerdev.artshelf.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val DEFAULT_RECENT_SEARCH_LIMIT = 10

class GetRecentSearchesUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository,
) {
    operator fun invoke(limit: Int = DEFAULT_RECENT_SEARCH_LIMIT): Flow<List<RecentSearch>> =
        recentSearchRepository.getRecentSearches(limit)
}
