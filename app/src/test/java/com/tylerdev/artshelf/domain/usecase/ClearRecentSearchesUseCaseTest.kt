package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.RecentSearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ClearRecentSearchesUseCaseTest {

    private val mockRecentSearchRepository = mockk<RecentSearchRepository>()
    private val clearRecentSearchesUseCase = ClearRecentSearchesUseCase(mockRecentSearchRepository)

    @Test
    fun `invoke clears all recent searches`() = runTest {
        coEvery { mockRecentSearchRepository.clearSearches() } returns Unit

        clearRecentSearchesUseCase()

        coVerify { mockRecentSearchRepository.clearSearches() }
    }
}
