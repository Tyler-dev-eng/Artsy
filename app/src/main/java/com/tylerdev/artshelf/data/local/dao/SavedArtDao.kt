package com.tylerdev.artshelf.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.tylerdev.artshelf.data.local.entity.SavedArtEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedArtDao {

    @Upsert
    suspend fun upsert(savedArt: SavedArtEntity)

    @Delete
    suspend fun delete(savedArt: SavedArtEntity)

    @Query("DELETE FROM ${SavedArtEntity.TABLE_NAME} WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM ${SavedArtEntity.TABLE_NAME} ORDER BY savedAtEpochMillis DESC")
    fun pagingSource(): PagingSource<Int, SavedArtEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM ${SavedArtEntity.TABLE_NAME} WHERE id = :id)")
    fun isSaved(id: Long): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM ${SavedArtEntity.TABLE_NAME})")
    fun hasSavedArt(): Flow<Boolean>

    @Query("SELECT id FROM ${SavedArtEntity.TABLE_NAME}")
    fun getAllIds(): Flow<List<Long>>

    @Query("SELECT * FROM ${SavedArtEntity.TABLE_NAME} WHERE id = :id")
    fun getById(id: Long): Flow<SavedArtEntity?>

    @Query("UPDATE ${SavedArtEntity.TABLE_NAME} SET notes = :notes WHERE id = :id")
    suspend fun updateNotes(id: Long, notes: String?)

    @Query("UPDATE ${SavedArtEntity.TABLE_NAME} SET title = :title, userName = :userName WHERE id = :id")
    suspend fun updateDetails(id: Long, title: String?, userName: String)
}
