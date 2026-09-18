package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UploadArtUseCaseTest {

    private val mockSavedArtRepository = mockk<SavedArtRepository>()
    private val uploadArtUseCase = UploadArtUseCase(mockSavedArtRepository)

    @Test
    fun `invoke delegates to repository and returns saved art`() = runTest {
        val sourceUriString = "content://media/external/images/media/1"
        val expectedArt = ArtImage(
            id = -1L,
            pageUrl = "file:///data/uploaded_art/1.jpg",
            previewUrl = "file:///data/uploaded_art/1.jpg",
            webformatUrl = "file:///data/uploaded_art/1.jpg",
            largeImageUrl = "file:///data/uploaded_art/1.jpg",
            tags = listOf("my upload"),
            userName = "You",
            likes = 0,
            downloads = 0,
        )
        coEvery { mockSavedArtRepository.saveUploadedArt(sourceUriString) } returns expectedArt

        val actualArt = uploadArtUseCase(sourceUriString)

        assertEquals(expectedArt, actualArt)
        coVerify { mockSavedArtRepository.saveUploadedArt(sourceUriString) }
    }
}
