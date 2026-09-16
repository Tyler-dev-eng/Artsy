package com.tylerdev.artshelf.domain.model

data class ArtImage(
    val id: Long,
    val pageUrl: String,
    val previewUrl: String,
    val webformatUrl: String,
    val largeImageUrl: String,
    val tags: List<String>,
    val userName: String,
    val likes: Int,
    val downloads: Int,
)
