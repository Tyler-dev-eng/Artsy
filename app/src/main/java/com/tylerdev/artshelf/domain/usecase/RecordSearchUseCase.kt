package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.RecentSearchRepository
import javax.inject.Inject

class RecordSearchUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository,
) {
    suspend operator fun invoke(term: String) {
        val trimmedTerm = term.trim()
        if (trimmedTerm.isEmpty()) return
        recentSearchRepository.recordSearch(trimmedTerm)
    }
}
