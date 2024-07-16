package com.sergeymikhovich.notes.feature.data.note.api

import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteEntity
import kotlinx.coroutines.flow.Flow

interface LocalNoteDataSource {

    suspend fun getAll() : List<NoteEntity>

    fun observeAll() : Flow<List<NoteEntity>>

    suspend fun getById(id: String) : NoteEntity?

    suspend fun deleteById(id: String)

    suspend fun deleteAll()

    suspend fun upsert(noteEntity: NoteEntity): NoteEntity?

    suspend fun upsertAll(noteEntities: List<NoteEntity>)
}