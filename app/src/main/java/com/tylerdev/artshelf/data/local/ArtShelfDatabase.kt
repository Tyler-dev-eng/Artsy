package com.tylerdev.artshelf.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tylerdev.artshelf.data.local.dao.SavedArtDao
import com.tylerdev.artshelf.data.local.entity.SavedArtEntity

@Database(
    entities = [SavedArtEntity::class],
    version = 2,
)
abstract class ArtShelfDatabase : RoomDatabase() {
    abstract fun savedArtDao(): SavedArtDao
}
