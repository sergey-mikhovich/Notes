package com.sergeymikhovich.notes.app.mediator.di

import com.sergeymikhovich.notes.app.mediator.NoteRouterImpl
import com.sergeymikhovich.notes.app.mediator.NotesRouterImpl
import com.sergeymikhovich.notes.feature.note.navigation.NoteRouter
import com.sergeymikhovich.notes.feature.notes.navigation.NotesRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface NoteRouterModule {

    @Binds
    fun bindNoteRouter(noteRouter: NoteRouterImpl): NoteRouter

    @Binds
    fun bindNotesRouter(notesRouter: NotesRouterImpl): NotesRouter
}