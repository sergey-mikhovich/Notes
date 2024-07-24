package com.sergeymikhovich.notes.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sergeymikhovich.notes.core.database.model.ChangeNoteEntity

@Dao
interface ChangeNoteDao {

    @Query("SELECT * FROM change_note WHERE lastModifiedTime > :time")
    suspend fun getChangeNotesAfter(time: Long): List<ChangeNoteEntity>

    @Query("SELECT * FROM change_note ORDER BY lastModifiedTime DESC LIMIT 1")
    suspend fun getLastChangeNote(): ChangeNoteEntity?

    @Upsert
    suspend fun upsert(changeNoteEntity: ChangeNoteEntity)

    @Upsert
    suspend fun upsertAll(changeNoteEntities: List<ChangeNoteEntity>)
}