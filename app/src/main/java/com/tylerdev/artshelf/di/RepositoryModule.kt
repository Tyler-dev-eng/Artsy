package com.tylerdev.artshelf.di

import com.tylerdev.artshelf.data.repository.ArtRepositoryImpl
import com.tylerdev.artshelf.data.repository.RecentSearchRepositoryImpl
import com.tylerdev.artshelf.data.repository.SavedArtRepositoryImpl
import com.tylerdev.artshelf.domain.repository.ArtRepository
import com.tylerdev.artshelf.domain.repository.RecentSearchRepository
import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindArtRepository(artRepositoryImpl: ArtRepositoryImpl): ArtRepository

    @Binds
    @Singleton
    abstract fun bindSavedArtRepository(savedArtRepositoryImpl: SavedArtRepositoryImpl): SavedArtRepository

    @Binds
    @Singleton
    abstract fun bindRecentSearchRepository(
        recentSearchRepositoryImpl: RecentSearchRepositoryImpl,
    ): RecentSearchRepository
}
