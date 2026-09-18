package com.tylerdev.artshelf.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tylerdev.artshelf.data.local.entity.SavedArtEntity

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE ${SavedArtEntity.TABLE_NAME} ADD COLUMN notes TEXT DEFAULT NULL")
    }
}
