package com.sergeymikhovich.notes.app.mediator.graph

import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.feature.note.composeToNote
import com.sergeymikhovich.notes.feature.notes.composableToNotes

fun NavGraphBuilder.composableAll() {
    composableToNotes()
    composeToNote()
}