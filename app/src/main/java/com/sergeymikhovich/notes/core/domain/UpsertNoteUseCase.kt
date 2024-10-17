package com.sergeymikhovich.notes.core.domain

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.andThenRecover
import com.sergeymikhovich.notes.core.data.repository.NoteRepository
import com.sergeymikhovich.notes.core.domain.UpsertResult.Error
import com.sergeymikhovich.notes.core.domain.UpsertResult.Success
import com.sergeymikhovich.notes.core.model.Note
import javax.inject.Inject

sealed interface UpsertResult {
    data object Success : UpsertResult
    data object EmptyNote : UpsertResult
    data object Error : UpsertResult
}

class UpsertNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note): Result<UpsertResult, Throwable> {
        if (note.isEmpty()) return Ok(UpsertResult.EmptyNote)

        return noteRepository.upsert(note)
            .andThen { if (it != null) Ok(Success) else Ok(Error) }
            .andThenRecover { Err(it) }
    }
}
