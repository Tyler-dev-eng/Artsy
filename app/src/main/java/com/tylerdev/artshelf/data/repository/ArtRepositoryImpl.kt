package com.tylerdev.artshelf.data.repository

import com.tylerdev.artshelf.data.remote.ArtApi
import com.tylerdev.artshelf.data.remote.dto.toArtImage
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.repository.ArtRepository
import com.tylerdev.artshelf.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val NETWORK_ERROR_MESSAGE = "Couldn't reach Pixabay. Check your connection."
private const val SERVER_ERROR_MESSAGE = "Something went wrong on the server."

class ArtRepositoryImpl
    @Inject
    constructor(
        private val artApi: ArtApi,
    ) : ArtRepository {
        override fun searchArt(query: String): Flow<Resource<List<ArtImage>>> =
            flow {
                emit(Resource.Loading())
                try {
                    val artImages = artApi.searchImages(query = query).hits.map { it.toArtImage() }
                    emit(Resource.Success(artImages))
                } catch (_: IOException) {
                    emit(Resource.Error(NETWORK_ERROR_MESSAGE))
                } catch (_: HttpException) {
                    emit(Resource.Error(SERVER_ERROR_MESSAGE))
                }
            }
    }
