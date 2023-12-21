package com.sergeymikhovich.notes.feature.data.note.impl.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sergeymikhovich.notes.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

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
class NoteDaoModule {

    @Provides
    @ApplicationScope
    fun provideNoteDao(database: NoteDatabase): NoteDao = database.noteDao()
}