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
}
