package com.sergeymikhovich.notes.feature.data.note.impl.db

interface NoteDatabase {
    fun noteDao() : NoteDao
}