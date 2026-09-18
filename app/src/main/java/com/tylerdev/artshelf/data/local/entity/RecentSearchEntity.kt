package com.tylerdev.artshelf.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = RecentSearchEntity.TABLE_NAME,
    indices = [Index(value = ["searchedAtEpochMillis"])],
)
data class RecentSearchEntity(
    @PrimaryKey
    val term: String,
    val searchedAtEpochMillis: Long,
) {
    companion object {
        const val TABLE_NAME = "recent_search"
    }
}
