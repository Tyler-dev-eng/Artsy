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
    val notes: String? = null,
    val title: String? = null,
)

private const val DEFAULT_TITLE_TAG_COUNT = 2

fun ArtImage.displayTitle(): String =
    title ?: tags.take(DEFAULT_TITLE_TAG_COUNT).joinToString(" ") { it.trim().uppercase() }
