package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.RecentSearchRepository
import javax.inject.Inject

class RemoveRecentSearchUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository,
) {
    suspend operator fun invoke(term: String) {
        recentSearchRepository.removeSearch(term)
    }
}
