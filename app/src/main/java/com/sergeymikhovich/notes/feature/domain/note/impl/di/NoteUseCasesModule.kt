package com.sergeymikhovich.notes.feature.domain.note.impl.di

import com.sergeymikhovich.notes.common.di.scope.ApplicationScope
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

@Module
interface NoteUseCasesModule {

    @Binds
    @ApplicationScope
    fun bindAddNoteUseCase(addNoteUseCase: AddNoteUseCaseImpl): AddNoteUseCase

    @Binds
    @ApplicationScope
    fun bindDeleteNoteUseCase(deleteNoteUseCase: DeleteNoteUseCaseImpl): DeleteNoteUseCase

    @Binds
    @ApplicationScope
    fun bindGetNotesUseCase(getNotesUseCase: GetNotesUseCaseImpl): GetNotesUseCase

    @Binds
    @ApplicationScope
    fun bindGetNoteUseCase(getNoteUseCase: GetNoteUseCaseImpl): GetNoteUseCase

    @Binds
    @ApplicationScope
    fun bindUpdateNoteUseCase(updateNoteUseCase: UpdateNoteUseCaseImpl): UpdateNoteUseCase
}