package com.sergeymikhovich.notes.feature.domain.note.impl

import com.sergeymikhovich.notes.feature.domain.note.api.GetNotesUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.ObserveNotesUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNotesUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : ObserveNotesUseCase {

    override fun invoke(): Flow<List<Note>> {
        return noteRepository.observeAll()
    }
}