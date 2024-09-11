package com.sergeymikhovich.notes.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sergeymikhovich.notes.core.database.model.ChangeNoteEntity

@Dao
interface ChangeNoteDao {

    @Query("SELECT * FROM change_note WHERE lastModifiedTime > :time AND userId = :userId")
    suspend fun getChangeNotesAfter(time: Long, userId: String): List<ChangeNoteEntity>

    @Query("SELECT * FROM change_note WHERE userId = :userId ORDER BY lastModifiedTime DESC LIMIT 1")
    suspend fun getLastChangeNote(userId: String): ChangeNoteEntity?

    @Upsert
    suspend fun upsert(changeNoteEntity: ChangeNoteEntity)

    @Upsert
    suspend fun upsertAll(changeNoteEntities: List<ChangeNoteEntity>)
}