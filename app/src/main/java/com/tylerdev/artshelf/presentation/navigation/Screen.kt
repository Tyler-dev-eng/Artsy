package com.tylerdev.artshelf.presentation.navigation

import android.net.Uri
import com.tylerdev.artshelf.domain.model.ArtImage

private const val ARTWORK_TAGS_DELIMITER = ","

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Search : Screen("search")
    data object Library : Screen("library")

    data object ArtworkDetail : Screen(
        "artworkDetail?id={id}&pageUrl={pageUrl}&previewUrl={previewUrl}" +
            "&webformatUrl={webformatUrl}&largeImageUrl={largeImageUrl}" +
            "&tags={tags}&userName={userName}&likes={likes}&downloads={downloads}",
    ) {
        fun createRoute(art: ArtImage): String =
            "artworkDetail?id=${art.id}" +
                "&pageUrl=${Uri.encode(art.pageUrl)}" +
                "&previewUrl=${Uri.encode(art.previewUrl)}" +
                "&webformatUrl=${Uri.encode(art.webformatUrl)}" +
                "&largeImageUrl=${Uri.encode(art.largeImageUrl)}" +
                "&tags=${Uri.encode(art.tags.joinToString(ARTWORK_TAGS_DELIMITER))}" +
                "&userName=${Uri.encode(art.userName)}" +
                "&likes=${art.likes}" +
                "&downloads=${art.downloads}"
    }
}
