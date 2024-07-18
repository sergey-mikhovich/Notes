package com.sergeymikhovich.notes.core.domain

import com.sergeymikhovich.notes.core.data.repository.NoteRepository
import com.sergeymikhovich.notes.core.model.Note
import javax.inject.Inject

sealed interface UpsertNoteResult {
    data object Success : UpsertNoteResult
    data object EmptyNote : UpsertNoteResult
    data object Fail : UpsertNoteResult
}

class UpsertNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(note: Note): UpsertNoteResult {
        if (note.isEmpty()) return UpsertNoteResult.EmptyNote

        val addedNote = noteRepository.upsert(note)
        return if (addedNote != null) UpsertNoteResult.Success else UpsertNoteResult.Fail
    }
}
