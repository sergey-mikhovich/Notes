package com.sergeymikhovich.notes.feature.domain.note.api

import com.sergeymikhovich.notes.feature.domain.note.api.model.Note

interface GetNotesUseCase {
    suspend operator fun invoke() : List<Note>
}