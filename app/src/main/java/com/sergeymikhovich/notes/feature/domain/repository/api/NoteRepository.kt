package com.sergeymikhovich.notes.feature.domain.repository.api

import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getAll() : List<Note>

    fun observeAll(): Flow<List<Note>>

    suspend fun getById(id: Long) : Note?

    suspend fun delete(id: Long)

    suspend fun add(note: Note): Note?

    suspend fun update(note: Note)
}