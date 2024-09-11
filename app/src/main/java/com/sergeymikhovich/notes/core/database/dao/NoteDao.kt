package com.sergeymikhovich.notes.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sergeymikhovich.notes.core.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE userId = :userId")
    fun observeAll(userId: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id IN (:ids) AND userId = :userId")
    fun getByIds(ids: List<String>, userId: String): List<NoteEntity>

    @Query("SELECT * FROM note WHERE id = :id AND userId = :userId")
    fun getById(id: String, userId: String): NoteEntity?

    @Query("DELETE FROM note WHERE id = :id")
    fun deleteById(id: String)

    @Query("DELETE FROM note WHERE id IN (:ids)")
    fun deleteByIds(ids: List<String>)

    @Upsert
    fun upsert(noteEntity: NoteEntity)

    @Upsert
    fun upsertAll(noteEntities: List<NoteEntity>)
}