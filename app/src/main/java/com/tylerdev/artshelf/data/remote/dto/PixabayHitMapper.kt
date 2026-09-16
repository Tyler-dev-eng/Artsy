package com.tylerdev.artshelf.data.remote.dto

import com.tylerdev.artshelf.domain.model.ArtImage

private const val TAGS_DELIMITER = ","

fun PixabayHitDto.toArtImage(): ArtImage = ArtImage(
    id = id,
    pageUrl = pageUrl,
    previewUrl = previewUrl,
    webformatUrl = webformatUrl,
    largeImageUrl = largeImageUrl,
    tags = tags.split(TAGS_DELIMITER).map { it.trim() },
    userName = user,
    likes = likes,
    downloads = downloads,
)
