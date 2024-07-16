package com.sergeymikhovich.notes.feature.data.note.impl.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM note")
    fun observeAll(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getById(id: String): NoteEntity?

    @Query("DELETE FROM note WHERE id = :id")
    fun deleteById(id: String)

    @Query("DELETE FROM note")
    fun deleteAll()

    @Upsert
    fun upsert(noteEntity: NoteEntity)

    @Upsert
    fun upsertAll(noteEntities: List<NoteEntity>)
}

@Module
@InstallIn(SingletonComponent::class)
class NoteDaoModule {

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao = database.noteDao()
}