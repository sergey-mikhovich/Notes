package com.sergeymikhovich.notes.app.mediator

import com.sergeymikhovich.notes.core.common.navigation.Navigator
import com.sergeymikhovich.notes.feature.auth.account_center.navigation.AccountCenterDirection
import com.sergeymikhovich.notes.feature.note.navigation.NoteDirection
import com.sergeymikhovich.notes.feature.notes.navigation.NotesRouter
import javax.inject.Inject

class NotesRouterImpl @Inject constructor(
    private val navigator: Navigator
): NotesRouter {

    override fun back() {
        navigator.navigateUp()
    }

    override fun toNote(noteId: String) {
        navigator.navigateTo(NoteDirection.createActionWithId(noteId))
    }

    override fun toCreateNote() {
        navigator.navigateTo(NoteDirection.createActionWithId())
    }

    override fun toAccountCenter() {
        navigator.navigateTo(AccountCenterDirection.createAction())
    }
}