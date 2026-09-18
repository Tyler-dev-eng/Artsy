package com.tylerdev.artshelf.domain.usecase

import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import javax.inject.Inject

private const val DEFAULT_ARTIST_NAME = "Unknown Artist"

class UpdateArtDetailsUseCase @Inject constructor(
    private val savedArtRepository: SavedArtRepository,
) {
    suspend operator fun invoke(id: Long, title: String?, userName: String) {
        val trimmedUserName = userName.trim().ifEmpty { DEFAULT_ARTIST_NAME }
        savedArtRepository.updateArtDetails(id, title?.trim()?.ifEmpty { null }, trimmedUserName)
    }
}
