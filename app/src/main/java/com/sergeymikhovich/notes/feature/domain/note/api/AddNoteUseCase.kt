package com.sergeymikhovich.notes.feature.domain.note.api

sealed interface AddNoteResult {
    data object Success : AddNoteResult
    data object EmptyNote : AddNoteResult
    data object FailOnAdd : AddNoteResult
}

interface AddNoteUseCase {
    suspend operator fun invoke(title: String, description: String) : AddNoteResult
}