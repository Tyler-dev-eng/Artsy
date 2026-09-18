package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdateArtNotesUseCaseTest {

    private val mockSavedArtRepository = mockk<SavedArtRepository>()
    private val updateArtNotesUseCase = UpdateArtNotesUseCase(mockSavedArtRepository)

    @Test
    fun `invoke trims notes before persisting`() = runTest {
        coEvery { mockSavedArtRepository.updateArtNotes(any(), any()) } returns Unit

        updateArtNotesUseCase(1L, "  hello world  ")

        coVerify { mockSavedArtRepository.updateArtNotes(1L, "hello world") }
    }

    @Test
    fun `invoke stores null when notes are blank`() = runTest {
        coEvery { mockSavedArtRepository.updateArtNotes(any(), any()) } returns Unit

        updateArtNotesUseCase(1L, "   ")

        coVerify { mockSavedArtRepository.updateArtNotes(1L, null) }
    }

    @Test
    fun `invoke stores null when notes are null`() = runTest {
        coEvery { mockSavedArtRepository.updateArtNotes(any(), any()) } returns Unit

        updateArtNotesUseCase(1L, null)

        coVerify { mockSavedArtRepository.updateArtNotes(1L, null) }
    }
}
