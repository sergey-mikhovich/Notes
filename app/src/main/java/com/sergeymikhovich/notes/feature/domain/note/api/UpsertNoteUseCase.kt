package com.sergeymikhovich.notes.feature.domain.note.api

import com.sergeymikhovich.notes.feature.domain.note.api.model.Note

sealed interface UpsertNoteResult {
    data object Success : UpsertNoteResult
    data object EmptyNote : UpsertNoteResult
    data object Fail : UpsertNoteResult
}

interface UpsertNoteUseCase {
    suspend operator fun invoke(note: Note) : UpsertNoteResult
}