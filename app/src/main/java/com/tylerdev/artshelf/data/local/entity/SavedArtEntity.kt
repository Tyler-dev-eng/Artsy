package com.tylerdev.artshelf.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = SavedArtEntity.TABLE_NAME,
    indices = [Index(value = ["savedAtEpochMillis"])],
)
data class SavedArtEntity(
    @PrimaryKey
    val id: Long,
    val pageUrl: String,
    val previewUrl: String,
    val webformatUrl: String,
    val largeImageUrl: String,
    val tagsCsv: String,
    val userName: String,
    val likes: Int,
    val downloads: Int,
    val savedAtEpochMillis: Long,
    val notes: String? = null,
    val title: String? = null,
) {
    companion object {
        const val TABLE_NAME = "saved_art"
    }
}
