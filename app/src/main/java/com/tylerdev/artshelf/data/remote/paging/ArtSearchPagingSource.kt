package com.tylerdev.artshelf.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tylerdev.artshelf.data.remote.ArtApi
import com.tylerdev.artshelf.data.remote.dto.toArtImage
import com.tylerdev.artshelf.domain.model.ArtImage
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class ArtSearchPagingSource(
    private val artApi: ArtApi,
    private val query: String,
) : PagingSource<Int, ArtImage>() {

    override fun getRefreshKey(state: PagingState<Int, ArtImage>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtImage> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = artApi.searchImages(query = query, page = page, perPage = params.loadSize)
            val artImages = response.hits.map { it.toArtImage() }
            LoadResult.Page(
                data = artImages,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (artImages.isEmpty()) null else page + 1,
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
