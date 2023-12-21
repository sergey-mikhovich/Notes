package com.sergeymikhovich.notes.feature.domain.note.impl

import com.sergeymikhovich.notes.feature.domain.note.api.UpdateNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : UpdateNoteUseCase {

    override suspend fun invoke(note: Note) {
        noteRepository.update(note)
    }
}