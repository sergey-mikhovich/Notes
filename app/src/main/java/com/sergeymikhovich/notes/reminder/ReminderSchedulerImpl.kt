package com.sergeymikhovich.notes.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import com.sergeymikhovich.notes.reminder.ReminderReceiver.Companion.REMINDER_MESSAGE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReminderSchedulerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ReminderScheduler {

    private val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)
        ?: throw IllegalStateException()

    override fun schedule(reminderItem: ReminderItem) {
        AlarmManagerCompat.setAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            reminderItem.alarmTime.time,
            getPendingIntent(reminderItem)
        )
    }

    override fun cancel(reminderItem: ReminderItem) {
        alarmManager.cancel(getPendingIntent(reminderItem))
    }

    private fun getPendingIntent(reminderItem: ReminderItem) =
        PendingIntent.getBroadcast(
            context,
            reminderItem.hashCode(),
            getIntent(reminderItem),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

    private fun getIntent(reminderItem: ReminderItem) =
        Intent(context, ReminderReceiver::class.java).apply {
            putExtra(REMINDER_MESSAGE, reminderItem.note)
        }
}