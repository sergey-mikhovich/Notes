package com.sergeymikhovich.notes.reminder

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.sergeymikhovich.notes.reminder.workers.ReminderWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WorkManagerReminderManager @Inject constructor(
    @ApplicationContext private val context: Context
): ReminderManager {

    override fun setReminder(noteId: String, initialDelaySec: Long) {
        WorkManager.getInstance(context).enqueueUniqueWork(
            noteId,
            ExistingWorkPolicy.KEEP,
            ReminderWorker.startReminderWorker(noteId, initialDelaySec)
        )
    }
}