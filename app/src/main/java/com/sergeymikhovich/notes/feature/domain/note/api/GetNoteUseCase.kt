package com.sergeymikhovich.notes.feature.domain.note.api

import com.sergeymikhovich.notes.feature.domain.note.api.model.Note

interface GetNoteUseCase {
    suspend operator fun invoke(id: String) : Note?
}