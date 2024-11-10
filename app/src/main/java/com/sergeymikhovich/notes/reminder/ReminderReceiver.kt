package com.sergeymikhovich.notes.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.IntentCompat
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.core.notification.Notifier
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ReminderReceiverEntryPoint {
    fun getNotifier(): Notifier
}

class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            ReminderReceiverEntryPoint::class.java
        )
        val notifier = entryPoint.getNotifier()

        IntentCompat.getParcelableExtra(intent, REMINDER_MESSAGE, Note::class.java)?.let { note ->
            notifier.postNotifications(listOf(note))
        }
    }

    companion object {
        const val REMINDER_MESSAGE = "ReminderMessage"
    }
}