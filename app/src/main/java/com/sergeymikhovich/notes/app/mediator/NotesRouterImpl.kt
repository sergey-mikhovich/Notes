package com.sergeymikhovich.notes.app.mediator

import android.util.Log
import com.sergeymikhovich.notes.common.navigation.api.Navigator
import com.sergeymikhovich.notes.feature.presentation.note.navigation.NoteDirection
import com.sergeymikhovich.notes.feature.presentation.notes.navigation.NotesDirection
import com.sergeymikhovich.notes.feature.presentation.notes.navigation.NotesRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

class NotesRouterImpl @Inject constructor(
    private val navigator: Navigator
): NotesRouter {

    override fun back() {
        navigator.navigateUp()
    }

    override fun toNote(noteId: Long) {
        navigator.navigateTo(NoteDirection.createAction(noteId))
    }

    override fun toCreateNote() {
        navigator.navigateTo(NoteDirection.createAction(0L))
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface NotesRouterModule {

    @Binds
    fun bindNotesRouter(notesRouter: NotesRouterImpl): NotesRouter
}