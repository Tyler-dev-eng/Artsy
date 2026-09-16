package com.tylerdev.artshelf.di

import com.tylerdev.artshelf.data.repository.ArtRepositoryImpl
import com.tylerdev.artshelf.domain.repository.ArtRepository
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
}
