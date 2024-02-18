package com.sergeymikhovich.notes.feature.domain.note.impl.di

import com.sergeymikhovich.notes.feature.domain.note.api.AddNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.DeleteNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.GetNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.GetNotesUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.UpdateNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.impl.AddNoteUseCaseImpl
import com.sergeymikhovich.notes.feature.domain.note.impl.DeleteNoteUseCaseImpl
import com.sergeymikhovich.notes.feature.domain.note.impl.GetNoteUseCaseImpl
import com.sergeymikhovich.notes.feature.domain.note.impl.GetNotesUseCaseImpl
import com.sergeymikhovich.notes.feature.domain.note.impl.UpdateNoteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NoteUseCasesModule {

    @Binds
    fun bindAddNoteUseCase(addNoteUseCase: AddNoteUseCaseImpl): AddNoteUseCase

    @Binds
    fun bindDeleteNoteUseCase(deleteNoteUseCase: DeleteNoteUseCaseImpl): DeleteNoteUseCase

    @Binds
    fun bindGetNotesUseCase(getNotesUseCase: GetNotesUseCaseImpl): GetNotesUseCase

    @Binds
    fun bindGetNoteUseCase(getNoteUseCase: GetNoteUseCaseImpl): GetNoteUseCase

    @Binds
    fun bindUpdateNoteUseCase(updateNoteUseCase: UpdateNoteUseCaseImpl): UpdateNoteUseCase
}