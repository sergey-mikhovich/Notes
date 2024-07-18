package com.sergeymikhovich.notes.core.data.repository

import com.sergeymikhovich.notes.core.data.Syncable
import com.sergeymikhovich.notes.core.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository: Syncable {

    fun observeAll(): Flow<List<Note>>

    suspend fun getById(id: String) : Note?

    suspend fun deleteById(id: String)

    suspend fun upsert(note: Note): Note?
}