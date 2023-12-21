package com.sergeymikhovich.notes.feature.domain.note.api

sealed interface AddNewCategoryResult {
    data object Success : AddNewCategoryResult
    data object EmptyNote : AddNewCategoryResult
    data object FailOnAdd : AddNewCategoryResult
}

interface AddNoteUseCase {
    suspend operator fun invoke(title: String, description: String) : AddNewCategoryResult
}