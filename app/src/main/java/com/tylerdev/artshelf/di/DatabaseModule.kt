package com.tylerdev.artshelf.di

import android.content.Context
import androidx.room.Room
import com.tylerdev.artshelf.data.local.ArtShelfDatabase
import com.tylerdev.artshelf.data.local.MIGRATION_1_2
import com.tylerdev.artshelf.data.local.MIGRATION_2_3
import com.tylerdev.artshelf.data.local.dao.RecentSearchDao
import com.tylerdev.artshelf.data.local.dao.SavedArtDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "artshelf.db"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideArtShelfDatabase(@ApplicationContext context: Context): ArtShelfDatabase =
        Room.databaseBuilder(context, ArtShelfDatabase::class.java, DATABASE_NAME)
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()

    @Provides
    @Singleton
    fun provideSavedArtDao(database: ArtShelfDatabase): SavedArtDao = database.savedArtDao()

    @Provides
    @Singleton
    fun provideRecentSearchDao(database: ArtShelfDatabase): RecentSearchDao = database.recentSearchDao()
}
