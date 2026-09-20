package com.tylerdev.artshelf.presentation.preview

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.tylerdev.artshelf.domain.model.ArtImage
import kotlinx.coroutines.flow.flowOf

private const val PREVIEW_LIKES = 128
private const val PREVIEW_DOWNLOADS = 42

val PREVIEW_ART_IMAGES =
    listOf(
        ArtImage(
            id = 1L,
            pageUrl = "https://pixabay.com/photos/1",
            previewUrl = "https://pixabay.com/preview/1",
            webformatUrl = "https://pixabay.com/webformat/1",
            largeImageUrl = "https://pixabay.com/large/1",
            tags = listOf("mural", "street art", "color"),
            userName = "graffitigallery",
            likes = PREVIEW_LIKES,
            downloads = PREVIEW_DOWNLOADS,
            notes = "Saw this on 5th and Main — reminds me of Haring.",
        ),
        ArtImage(
            id = 2L,
            pageUrl = "https://pixabay.com/photos/2",
            previewUrl = "https://pixabay.com/preview/2",
            webformatUrl = "https://pixabay.com/webformat/2",
            largeImageUrl = "https://pixabay.com/large/2",
            tags = listOf("abstract", "canvas"),
            userName = "inkblotstudio",
            likes = PREVIEW_LIKES,
            downloads = PREVIEW_DOWNLOADS,
        ),
    )

@Composable
fun previewArtItems(): LazyPagingItems<ArtImage> {
    val pagingData = PagingData.from(PREVIEW_ART_IMAGES)
    return flowOf(pagingData).collectAsLazyPagingItems()
}
