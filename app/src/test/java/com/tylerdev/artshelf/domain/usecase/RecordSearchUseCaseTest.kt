package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.RecentSearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RecordSearchUseCaseTest {

    private val mockRecentSearchRepository = mockk<RecentSearchRepository>()
    private val recordSearchUseCase = RecordSearchUseCase(mockRecentSearchRepository)

    @Test
    fun `invoke records trimmed term`() = runTest {
        coEvery { mockRecentSearchRepository.recordSearch(any()) } returns Unit

        recordSearchUseCase("  yellow flowers  ")

        coVerify { mockRecentSearchRepository.recordSearch("yellow flowers") }
    }

    @Test
    fun `invoke ignores blank term`() = runTest {
        recordSearchUseCase("   ")

        coVerify(exactly = 0) { mockRecentSearchRepository.recordSearch(any()) }
    }
}
