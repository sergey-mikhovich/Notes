package com.sergeymikhovich.notes.feature.domain.note.impl

import com.sergeymikhovich.notes.feature.domain.note.api.AddNewCategoryResult
import com.sergeymikhovich.notes.feature.domain.note.api.AddNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.model.NewNote
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import javax.inject.Inject

class AddNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : AddNoteUseCase {

    override suspend fun invoke(title: String, description: String): AddNewCategoryResult {
        if (title.isEmpty() && description.isEmpty()) return AddNewCategoryResult.EmptyNote

        val addedNote = noteRepository.add(NewNote(title, description))
        return if (addedNote != null) AddNewCategoryResult.Success else AddNewCategoryResult.FailOnAdd
    }
}