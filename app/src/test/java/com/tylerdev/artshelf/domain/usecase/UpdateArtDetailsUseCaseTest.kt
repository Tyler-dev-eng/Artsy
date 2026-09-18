package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdateArtDetailsUseCaseTest {

    private val mockSavedArtRepository = mockk<SavedArtRepository>()
    private val updateArtDetailsUseCase = UpdateArtDetailsUseCase(mockSavedArtRepository)

    @Test
    fun `invoke trims title and userName before persisting`() = runTest {
        coEvery { mockSavedArtRepository.updateArtDetails(any(), any(), any()) } returns Unit

        updateArtDetailsUseCase(1L, "  Sunset Study  ", "  Ada Lovelace  ")

        coVerify { mockSavedArtRepository.updateArtDetails(1L, "Sunset Study", "Ada Lovelace") }
    }

    @Test
    fun `invoke stores null title when title is blank`() = runTest {
        coEvery { mockSavedArtRepository.updateArtDetails(any(), any(), any()) } returns Unit

        updateArtDetailsUseCase(1L, "   ", "Ada Lovelace")

        coVerify { mockSavedArtRepository.updateArtDetails(1L, null, "Ada Lovelace") }
    }

    @Test
    fun `invoke falls back to a default artist name when userName is blank`() = runTest {
        coEvery { mockSavedArtRepository.updateArtDetails(any(), any(), any()) } returns Unit

        updateArtDetailsUseCase(1L, "Sunset Study", "   ")

        coVerify { mockSavedArtRepository.updateArtDetails(1L, "Sunset Study", "Unknown Artist") }
    }
}
