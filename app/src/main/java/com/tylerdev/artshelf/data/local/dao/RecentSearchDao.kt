package com.tylerdev.artshelf.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tylerdev.artshelf.data.local.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {

    @Upsert
    suspend fun upsert(recentSearch: RecentSearchEntity)

    @Query("DELETE FROM ${RecentSearchEntity.TABLE_NAME} WHERE term = :term")
    suspend fun deleteByTerm(term: String)

    @Query("DELETE FROM ${RecentSearchEntity.TABLE_NAME}")
    suspend fun deleteAll()

    @Query("SELECT * FROM ${RecentSearchEntity.TABLE_NAME} ORDER BY searchedAtEpochMillis DESC LIMIT :limit")
    fun getRecent(limit: Int): Flow<List<RecentSearchEntity>>
}
