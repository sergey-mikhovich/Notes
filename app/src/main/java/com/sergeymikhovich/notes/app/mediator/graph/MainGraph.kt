package com.sergeymikhovich.notes.app.mediator.graph

import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.feature.presentation.note.composeToNote
import com.sergeymikhovich.notes.feature.presentation.notes.composableToNotes

fun NavGraphBuilder.composableAll() {
    composableToNotes()
    composeToNote()
}