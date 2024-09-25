package com.sergeymikhovich.notes.reminder.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.sergeymikhovich.notes.core.common.di.Dispatcher
import com.sergeymikhovich.notes.core.common.di.NoteDispatchers.IO
import com.sergeymikhovich.notes.core.data.repository.NoteRepository
import com.sergeymikhovich.notes.core.notification.Notifier
import com.sergeymikhovich.notes.sync.workers.DelegatingWorker
import com.sergeymikhovich.notes.sync.workers.delegateData
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

private const val REMINDER_NOTE_ID = "ReminderNoteId"

@HiltWorker
internal class ReminderWorker @AssistedInject constructor (
    @Assisted private val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository,
    private val notifier: Notifier
): CoroutineWorker(appContext, workerParameters) {

    private val reminderNoteId =
        workerParameters.inputData.getString(REMINDER_NOTE_ID) ?: ""

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val note = noteRepository.getById(reminderNoteId)
        if (note != null) {
            notifier.postNotifications(listOf(note))
            Result.success()
        } else {
            Result.failure()
        }
    }

    companion object {
        fun startReminderWorker(noteId: String, initialDelaySec: Long) =
            OneTimeWorkRequestBuilder<DelegatingWorker>()
                .setInitialDelay(initialDelaySec, TimeUnit.SECONDS)
                .setInputData(
                    ReminderWorker::class.delegateData {
                        putString(REMINDER_NOTE_ID, noteId)
                    }
                )
                .build()
    }
}