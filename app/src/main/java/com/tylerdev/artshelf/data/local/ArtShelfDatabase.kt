package com.tylerdev.artshelf.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tylerdev.artshelf.data.local.dao.RecentSearchDao
import com.tylerdev.artshelf.data.local.dao.SavedArtDao
import com.tylerdev.artshelf.data.local.entity.RecentSearchEntity
import com.tylerdev.artshelf.data.local.entity.SavedArtEntity

@Database(
    entities = [SavedArtEntity::class, RecentSearchEntity::class],
    version = 4,
)
abstract class ArtShelfDatabase : RoomDatabase() {
    abstract fun savedArtDao(): SavedArtDao
    abstract fun recentSearchDao(): RecentSearchDao
}
