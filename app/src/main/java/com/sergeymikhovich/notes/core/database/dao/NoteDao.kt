package com.sergeymikhovich.notes.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sergeymikhovich.notes.core.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun observeAll(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id IN (:ids)")
    fun getByIds(ids: List<String>): List<NoteEntity>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getById(id: String): NoteEntity?

    @Query("DELETE FROM note WHERE id = :id")
    fun deleteById(id: String)

    @Query("DELETE FROM note WHERE id IN (:ids)")
    fun deleteByIds(ids: List<String>)

    @Upsert
    fun upsert(noteEntity: NoteEntity)

    @Upsert
    fun upsertAll(noteEntities: List<NoteEntity>)
}