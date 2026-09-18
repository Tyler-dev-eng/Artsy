package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.RecentSearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RemoveRecentSearchUseCaseTest {

    private val mockRecentSearchRepository = mockk<RecentSearchRepository>()
    private val removeRecentSearchUseCase = RemoveRecentSearchUseCase(mockRecentSearchRepository)

    @Test
    fun `invoke removes the given term`() = runTest {
        coEvery { mockRecentSearchRepository.removeSearch(any()) } returns Unit

        removeRecentSearchUseCase("yellow flowers")

        coVerify { mockRecentSearchRepository.removeSearch("yellow flowers") }
    }
}
