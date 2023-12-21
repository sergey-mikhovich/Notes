package com.sergeymikhovich.notes.feature.domain.note.api

import com.sergeymikhovich.notes.feature.domain.note.api.model.Note

interface DeleteNoteUseCase {
    suspend operator fun invoke(note: Note)
}