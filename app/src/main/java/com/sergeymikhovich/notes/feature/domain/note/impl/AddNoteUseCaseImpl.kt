package com.sergeymikhovich.notes.feature.domain.note.impl

import com.sergeymikhovich.notes.feature.domain.note.api.AddNoteResult
import com.sergeymikhovich.notes.feature.domain.note.api.AddNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import javax.inject.Inject

class AddNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : AddNoteUseCase {

    override suspend fun invoke(title: String, description: String): AddNoteResult {
        if (title.isEmpty() && description.isEmpty()) return AddNoteResult.EmptyNote

        val addedNote = noteRepository.add(Note(title, description))
        return if (addedNote != null) AddNoteResult.Success else AddNoteResult.FailOnAdd
    }
}