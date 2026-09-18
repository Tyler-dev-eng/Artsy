package com.tylerdev.artshelf.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetSavedArtByIdUseCaseTest {

    private val mockSavedArtRepository = mockk<SavedArtRepository>()
    private val getSavedArtByIdUseCase = GetSavedArtByIdUseCase(mockSavedArtRepository)

    @Test
    fun `invoke returns art from repository when it exists`() = runTest {
        val expectedArt = buildArtImage(id = 1L)
        every { mockSavedArtRepository.getArtById(1L) } returns flowOf(expectedArt)

        getSavedArtByIdUseCase(1L).test {
            assertThat(awaitItem()).isEqualTo(expectedArt)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns null when art is not saved`() = runTest {
        every { mockSavedArtRepository.getArtById(2L) } returns flowOf(null)

        getSavedArtByIdUseCase(2L).test {
            assertThat(awaitItem()).isNull()
            awaitComplete()
        }
    }

    private fun buildArtImage(id: Long) = ArtImage(
        id = id,
        pageUrl = "https://example.com/page",
        previewUrl = "https://example.com/preview",
        webformatUrl = "https://example.com/webformat",
        largeImageUrl = "https://example.com/large",
        tags = listOf("art"),
        userName = "artist",
        likes = 10,
        downloads = 5,
        notes = "my notes",
    )
}
