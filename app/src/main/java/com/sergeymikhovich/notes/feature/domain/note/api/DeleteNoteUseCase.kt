package com.sergeymikhovich.notes.feature.domain.note.api

interface DeleteNoteUseCase {
    suspend operator fun invoke(id: String)
}