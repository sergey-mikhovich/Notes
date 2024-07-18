package com.sergeymikhovich.notes.app.mediator

import com.sergeymikhovich.notes.core.common.ui.Navigator
import com.sergeymikhovich.notes.feature.note.navigation.NoteDirection
import com.sergeymikhovich.notes.feature.note.navigation.NoteRouter
import com.sergeymikhovich.notes.feature.notes.navigation.NotesRouter
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

    override fun toNote(noteId: String) {
        navigator.navigateTo(NoteDirection.createAction(noteId))
    }

    override fun toCreateNote() {
        navigator.navigateTo(NoteDirection.createAction())
    }
}