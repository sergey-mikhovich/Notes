package com.sergeymikhovich.notes.feature.domain.note.api

import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import kotlinx.coroutines.flow.Flow

interface ObserveNotesUseCase {
    operator fun invoke() : Flow<List<Note>>
}