package com.sergeymikhovich.notes.reminder

interface ReminderManager {
    fun setReminder(noteId: String, initialDelaySec: Long)
}