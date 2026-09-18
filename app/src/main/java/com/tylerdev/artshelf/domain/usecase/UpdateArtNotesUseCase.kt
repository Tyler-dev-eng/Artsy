package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import javax.inject.Inject

class UpdateArtNotesUseCase @Inject constructor(
    private val savedArtRepository: SavedArtRepository,
) {
    suspend operator fun invoke(id: Long, notes: String?) {
        savedArtRepository.updateArtNotes(id, notes?.trim()?.ifEmpty { null })
    }
}
