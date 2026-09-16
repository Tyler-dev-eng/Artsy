package com.tylerdev.artshelf.data.remote

import com.tylerdev.artshelf.data.remote.dto.PixabaySearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtApi {

    @GET(".")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("image_type") imageType: String = DEFAULT_IMAGE_TYPE,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("per_page") perPage: Int = DEFAULT_PER_PAGE,
    ): PixabaySearchResponseDto

    companion object {
        const val DEFAULT_IMAGE_TYPE = "photo"
        private const val DEFAULT_PAGE = 1
        private const val DEFAULT_PER_PAGE = 20
    }
}