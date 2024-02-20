package com.sergeymikhovich.notes.feature.data.note.api

import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import kotlinx.coroutines.flow.Flow

interface LocalNoteDataSource {

    suspend fun getAll() : List<Note>

    fun observeAll() : Flow<List<Note>>

    suspend fun getById(id: String) : Note?

    suspend fun delete(id: String)

    suspend fun add(note: Note): Note?

    suspend fun update(note: Note)
}