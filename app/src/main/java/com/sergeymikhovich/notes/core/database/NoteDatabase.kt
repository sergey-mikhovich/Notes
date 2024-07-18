package com.sergeymikhovich.notes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergeymikhovich.notes.core.database.dao.NoteDao
import com.sergeymikhovich.notes.core.database.model.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}