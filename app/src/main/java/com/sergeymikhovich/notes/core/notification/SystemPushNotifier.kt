package com.sergeymikhovich.notes.core.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.ui.util.fastForEachIndexed
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.InboxStyle
import androidx.core.app.NotificationManagerCompat
import com.sergeymikhovich.notes.R
import com.sergeymikhovich.notes.core.model.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val MAX_NOTIFICATIONS_COUNT = 5
private const val CHANNEL_ID = "notes_reminders"
private const val GROUP_ID = "notes_group"
private const val SUMMARY_ID = 1

class SystemPushNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) : Notifier {

    override fun postNotifications(notes: List<Note>) =
        with(context) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) return@with

            val truncatedNotes = notes.take(MAX_NOTIFICATIONS_COUNT)

            val notifications = truncatedNotes.map { note ->
                createNotesNotification {
                    setSmallIcon(R.drawable.google_g)
                    setContentTitle(note.title)
                    setContentText(note.description)
                    setStyle(
                        NotificationCompat.BigTextStyle()
                            .bigText(note.description)
                    )
                    .setGroup(GROUP_ID)
                }
            }

            val summaryNotification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Summary")
                .setContentText("New messages")
                .setSmallIcon(R.drawable.google_g)
                .setStyle(notesNotificationStyle(truncatedNotes, "Notes"))
                .setGroup(GROUP_ID)
                .setGroupSummary(true)
                .build()

            NotificationManagerCompat.from(this).apply {
                notifications.fastForEachIndexed { index, notification ->
                    notify(truncatedNotes[index].id.hashCode(), notification)
                }
                notify(SUMMARY_ID, summaryNotification)
            }
        }
}

private fun Context.createNotesNotification(
    block: NotificationCompat.Builder.() -> Unit
): Notification {
    ensureNotificationChannelExist()
    return NotificationCompat.Builder(this, CHANNEL_ID)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .apply(block)
        .build()
}

private fun Context.ensureNotificationChannelExist() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

    val channel = NotificationChannel(
        CHANNEL_ID,
        "Reminders",
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = ""
    }

    NotificationManagerCompat.from(this).createNotificationChannel(channel)
}

private fun notesNotificationStyle(
    notes: List<Note>,
    title: String
): InboxStyle =
    notes.fold(InboxStyle()) { inboxStyle, note -> inboxStyle.addLine(note.title) }
        .setBigContentTitle(title)
        .setSummaryText(title)