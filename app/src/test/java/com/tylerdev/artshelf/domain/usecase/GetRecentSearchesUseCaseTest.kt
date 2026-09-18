package com.tylerdev.artshelf.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.tylerdev.artshelf.domain.model.RecentSearch
import com.tylerdev.artshelf.domain.repository.RecentSearchRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetRecentSearchesUseCaseTest {

    private val mockRecentSearchRepository = mockk<RecentSearchRepository>()
    private val getRecentSearchesUseCase = GetRecentSearchesUseCase(mockRecentSearchRepository)

    @Test
    fun `invoke delegates default limit to repository`() = runTest {
        val expected = listOf(RecentSearch("yellow flowers", 1_000L))
        every { mockRecentSearchRepository.getRecentSearches(10) } returns flowOf(expected)

        getRecentSearchesUseCase().test {
            assertThat(awaitItem()).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `invoke forwards a custom limit`() = runTest {
        every { mockRecentSearchRepository.getRecentSearches(3) } returns flowOf(emptyList())

        getRecentSearchesUseCase(limit = 3).test {
            assertThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }
}
