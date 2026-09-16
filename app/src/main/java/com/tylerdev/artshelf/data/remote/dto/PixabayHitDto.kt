package com.tylerdev.artshelf.data.remote.dto

import com.squareup.moshi.Json

data class PixabayHitDto(
    @param:Json(name = "id") val id: Long,
    @param:Json(name = "pageURL") val pageUrl: String,
    @param:Json(name = "type") val type: String,
    @param:Json(name = "tags") val tags: String,
    @param:Json(name = "previewURL") val previewUrl: String,
    @param:Json(name = "previewWidth") val previewWidth: Int,
    @param:Json(name = "previewHeight") val previewHeight: Int,
    @param:Json(name = "webformatURL") val webformatUrl: String,
    @param:Json(name = "webformatWidth") val webformatWidth: Int,
    @param:Json(name = "webformatHeight") val webformatHeight: Int,
    @param:Json(name = "largeImageURL") val largeImageUrl: String,
    @param:Json(name = "imageWidth") val imageWidth: Int,
    @param:Json(name = "imageHeight") val imageHeight: Int,
    @param:Json(name = "imageSize") val imageSize: Long,
    @param:Json(name = "views") val views: Int,
    @param:Json(name = "downloads") val downloads: Int,
    @param:Json(name = "collections") val collections: Int,
    @param:Json(name = "likes") val likes: Int,
    @param:Json(name = "comments") val comments: Int,
    @param:Json(name = "user_id") val userId: Long,
    @param:Json(name = "user") val user: String,
    @param:Json(name = "userImageURL") val userImageUrl: String,
    @param:Json(name = "noAiTraining") val noAiTraining: Boolean,
    @param:Json(name = "isAiGenerated") val isAiGenerated: Boolean,
    @param:Json(name = "isGRated") val isGRated: Boolean,
    @param:Json(name = "isLowQuality") val isLowQuality: Boolean,
    @param:Json(name = "userURL") val userUrl: String,
    @param:Json(name = "name") val name: String,
)
