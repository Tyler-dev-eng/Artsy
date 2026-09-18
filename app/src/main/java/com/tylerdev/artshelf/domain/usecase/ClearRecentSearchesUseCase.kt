package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.RecentSearchRepository
import javax.inject.Inject

class ClearRecentSearchesUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository,
) {
    suspend operator fun invoke() {
        recentSearchRepository.clearSearches()
    }
}
