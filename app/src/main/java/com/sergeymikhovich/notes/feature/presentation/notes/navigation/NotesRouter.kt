package com.sergeymikhovich.notes.feature.presentation.notes.navigation

interface NotesRouter {

    fun back()

    fun toNote(noteId: String)

    fun toCreateNote()
}