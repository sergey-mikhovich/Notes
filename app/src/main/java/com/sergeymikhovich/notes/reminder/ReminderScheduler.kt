package com.sergeymikhovich.notes.reminder

interface ReminderScheduler {
    fun schedule(reminderItem: ReminderItem)
    fun cancel(reminderItem: ReminderItem)
}