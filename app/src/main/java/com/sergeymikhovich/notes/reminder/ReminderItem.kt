package com.sergeymikhovich.notes.reminder

import com.sergeymikhovich.notes.core.model.Note
import java.util.Date

data class ReminderItem(
    val alarmTime: Date,
    val note: Note
)
