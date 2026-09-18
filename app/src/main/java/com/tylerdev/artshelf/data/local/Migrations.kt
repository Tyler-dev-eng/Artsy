package com.tylerdev.artshelf.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tylerdev.artshelf.data.local.entity.RecentSearchEntity
import com.tylerdev.artshelf.data.local.entity.SavedArtEntity

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE ${SavedArtEntity.TABLE_NAME} ADD COLUMN notes TEXT DEFAULT NULL")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS ${RecentSearchEntity.TABLE_NAME} (" +
                "term TEXT NOT NULL PRIMARY KEY, " +
                "searchedAtEpochMillis INTEGER NOT NULL)",
        )
        db.execSQL(
            "CREATE INDEX IF NOT EXISTS index_${RecentSearchEntity.TABLE_NAME}_searchedAtEpochMillis " +
                "ON ${RecentSearchEntity.TABLE_NAME} (searchedAtEpochMillis)",
        )
    }
}
