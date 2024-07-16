package com.sergeymikhovich.notes.feature.data.note.api

import com.sergeymikhovich.notes.feature.data.note.impl.remote.NoteFirebase
import kotlinx.coroutines.flow.Flow

interface RemoteNoteDataSource {

    fun observeAll(): Flow<List<NoteFirebase>>

    suspend fun loadAll(): List<NoteFirebase>

    suspend fun deleteById(id: String)

    suspend fun upsert(noteFirebase: NoteFirebase)
}