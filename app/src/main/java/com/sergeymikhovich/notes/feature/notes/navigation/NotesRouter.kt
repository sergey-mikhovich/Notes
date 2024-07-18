package com.sergeymikhovich.notes.feature.notes.navigation

interface NotesRouter {

    fun back()

    fun toNote(noteId: String)

    fun toCreateNote()
}