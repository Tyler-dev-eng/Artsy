package com.tylerdev.artshelf.data.repository

import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tylerdev.artshelf.data.local.dao.SavedArtDao
import com.tylerdev.artshelf.data.local.entity.toArtImage
import com.tylerdev.artshelf.data.local.entity.toSavedArtEntity
import com.tylerdev.artshelf.domain.model.ArtImage
import com.tylerdev.artshelf.domain.repository.SavedArtRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import javax.inject.Inject

private const val PAGE_SIZE = 20
private const val UPLOADED_ART_DIR_NAME = "uploaded_art"
private const val UPLOADED_ART_USER_NAME = "You"
private const val UPLOADED_ART_TAG = "my upload"

class SavedArtRepositoryImpl
    @Inject
    constructor(
        private val savedArtDao: SavedArtDao,
        @ApplicationContext private val context: Context,
    ) : SavedArtRepository {

        override fun getSavedArt(): Flow<PagingData<ArtImage>> =
            Pager(
                config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
                pagingSourceFactory = { savedArtDao.pagingSource() },
            ).flow.map { pagingData -> pagingData.map { it.toArtImage() } }

        override fun isArtSaved(id: Long): Flow<Boolean> = savedArtDao.isSaved(id)

        override fun hasSavedArt(): Flow<Boolean> = savedArtDao.hasSavedArt()

        override fun getSavedArtIds(): Flow<Set<Long>> = savedArtDao.getAllIds().map { it.toSet() }

        override fun getArtById(id: Long): Flow<ArtImage?> =
            savedArtDao.getById(id).map { it?.toArtImage() }

        override suspend fun saveArt(artImage: ArtImage) {
            savedArtDao.upsert(artImage.toSavedArtEntity(savedAtEpochMillis = System.currentTimeMillis()))
        }

        override suspend fun saveUploadedArt(sourceUriString: String): ArtImage =
            withContext(Dispatchers.IO) {
                val localImageUrl = copyToInternalStorage(Uri.parse(sourceUriString))
                val artImage = ArtImage(
                    id = -System.nanoTime(),
                    pageUrl = localImageUrl,
                    previewUrl = localImageUrl,
                    webformatUrl = localImageUrl,
                    largeImageUrl = localImageUrl,
                    tags = listOf(UPLOADED_ART_TAG),
                    userName = UPLOADED_ART_USER_NAME,
                    likes = 0,
                    downloads = 0,
                )
                savedArtDao.upsert(artImage.toSavedArtEntity(savedAtEpochMillis = System.currentTimeMillis()))
                artImage
            }

        private fun copyToInternalStorage(sourceUri: Uri): String {
            val uploadedArtDir = File(context.filesDir, UPLOADED_ART_DIR_NAME).apply { mkdirs() }
            val destinationFile = File(uploadedArtDir, "${UUID.randomUUID()}.jpg")
            context.contentResolver.openInputStream(sourceUri)?.use { input ->
                destinationFile.outputStream().use { output -> input.copyTo(output) }
            }
            return Uri.fromFile(destinationFile).toString()
        }

        override suspend fun removeArt(id: Long) {
            savedArtDao.deleteById(id)
        }

        override suspend fun updateArtNotes(id: Long, notes: String?) {
            savedArtDao.updateNotes(id, notes)
        }
    }
