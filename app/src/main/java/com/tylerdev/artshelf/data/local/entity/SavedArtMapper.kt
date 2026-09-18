package com.tylerdev.artshelf.data.local.entity

import com.tylerdev.artshelf.domain.model.ArtImage

private const val TAGS_DELIMITER = ","

fun SavedArtEntity.toArtImage(): ArtImage = ArtImage(
    id = id,
    pageUrl = pageUrl,
    previewUrl = previewUrl,
    webformatUrl = webformatUrl,
    largeImageUrl = largeImageUrl,
    tags = tagsCsv.split(TAGS_DELIMITER).map { it.trim() },
    userName = userName,
    likes = likes,
    downloads = downloads,
    notes = notes,
)

fun ArtImage.toSavedArtEntity(savedAtEpochMillis: Long): SavedArtEntity = SavedArtEntity(
    id = id,
    pageUrl = pageUrl,
    previewUrl = previewUrl,
    webformatUrl = webformatUrl,
    largeImageUrl = largeImageUrl,
    tagsCsv = tags.joinToString(TAGS_DELIMITER),
    userName = userName,
    likes = likes,
    downloads = downloads,
    savedAtEpochMillis = savedAtEpochMillis,
    notes = notes,
)
