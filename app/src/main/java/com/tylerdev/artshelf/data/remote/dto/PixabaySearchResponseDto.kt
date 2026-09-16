package com.tylerdev.artshelf.data.remote.dto

import com.squareup.moshi.Json

data class PixabaySearchResponseDto(
    @param:Json(name = "total") val total: Int,
    @param:Json(name = "totalHits") val totalHits: Int,
    @param:Json(name = "hits") val hits: List<PixabayHitDto>,
)
