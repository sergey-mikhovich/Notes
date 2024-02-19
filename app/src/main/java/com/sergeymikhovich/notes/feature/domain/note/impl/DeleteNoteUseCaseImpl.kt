package com.sergeymikhovich.notes.feature.domain.note.impl

import com.sergeymikhovich.notes.feature.domain.note.api.DeleteNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : DeleteNoteUseCase {

    override suspend fun invoke(id: Long) {
        noteRepository.delete(id)
    }
}