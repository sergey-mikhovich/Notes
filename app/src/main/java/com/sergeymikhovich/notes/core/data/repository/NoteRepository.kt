package com.sergeymikhovich.notes.core.data.repository

import com.github.michaelbull.result.Result
import com.sergeymikhovich.notes.core.common.error_handling.UnitResult
import com.sergeymikhovich.notes.core.data.Syncable
import com.sergeymikhovich.notes.core.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository: Syncable {

    fun observeAll(): Flow<List<Note>>

    suspend fun getById(id: String) : Result<Note?, Throwable>

    suspend fun deleteById(id: String): UnitResult

    suspend fun upsert(note: Note): Result<Note?, Throwable>
}