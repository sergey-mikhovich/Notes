package com.sergeymikhovich.notes.feature.domain.note.impl

import com.sergeymikhovich.notes.feature.domain.note.api.UpsertNoteResult
import com.sergeymikhovich.notes.feature.domain.note.api.UpsertNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class UpsertNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : UpsertNoteUseCase {

    override suspend fun invoke(note: Note): UpsertNoteResult {
        if (note.isEmpty()) return UpsertNoteResult.EmptyNote

        val addedNote = noteRepository.upsert(note)
        return if (addedNote != null) UpsertNoteResult.Success else UpsertNoteResult.Fail
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface UpsertNoteUseCaseModule {

    @Binds
    fun bindUpsertNoteUseCase(upsertNoteUseCase: UpsertNoteUseCaseImpl): UpsertNoteUseCase
}
