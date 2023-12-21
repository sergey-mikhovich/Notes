package com.sergeymikhovich.notes.feature.domain.note.impl

import com.sergeymikhovich.notes.feature.domain.note.api.GetNotesUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import javax.inject.Inject

class GetNotesUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : GetNotesUseCase {

    override suspend fun invoke(): List<Note> {
        return noteRepository.getAll()
    }
}