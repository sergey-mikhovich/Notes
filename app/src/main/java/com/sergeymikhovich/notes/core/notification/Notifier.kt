package com.sergeymikhovich.notes.core.notification

import com.sergeymikhovich.notes.core.model.Note

interface Notifier {
    fun postNotifications(notes: List<Note>)
}