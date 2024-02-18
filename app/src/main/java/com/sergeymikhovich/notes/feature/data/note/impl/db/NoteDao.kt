package com.sergeymikhovich.notes.feature.data.note.impl.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getById(id: Long): NoteEntity?

    @Delete
    fun delete(note: NoteEntity)

    @Insert
    fun add(note: NoteEntity): Long

    @Update
    fun update(note: NoteEntity)
}

@Module
@InstallIn(SingletonComponent::class)
class NoteDaoModule {

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao = database.noteDao()
}