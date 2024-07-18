package com.sergeymikhovich.notes.app.mediator

import com.sergeymikhovich.notes.core.common.navigation.Navigator
import com.sergeymikhovich.notes.feature.note.navigation.NoteRouter
import javax.inject.Inject

class NoteRouterImpl @Inject constructor(
    private val navigator: Navigator
): NoteRouter {

    override fun back() {
        navigator.navigateUp()
    }
}