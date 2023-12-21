package com.sergeymikhovich.notes.feature.data.note.api

import com.sergeymikhovich.notes.feature.domain.note.api.model.NewNote
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note

interface LocalNoteDataSource {

    suspend fun getAll() : List<Note>

    suspend fun getById(id: Long) : Note?

    suspend fun delete(note: Note)

    suspend fun add(newNote: NewNote): Note?

    suspend fun update(note: Note)
}