package com.sergeymikhovich.notes.feature.domain.note.api

import com.sergeymikhovich.notes.feature.domain.note.api.model.Note

interface UpdateNoteUseCase {
    suspend operator fun invoke(id: String, title: String, description: String)
}