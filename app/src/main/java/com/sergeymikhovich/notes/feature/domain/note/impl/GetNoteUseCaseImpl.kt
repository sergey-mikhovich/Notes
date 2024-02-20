package com.sergeymikhovich.notes.feature.domain.note.impl

import com.sergeymikhovich.notes.feature.domain.note.api.GetNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import javax.inject.Inject

class GetNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : GetNoteUseCase {

    override suspend fun invoke(id: String): Note? {
        return noteRepository.getById(id)
    }
}