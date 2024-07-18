package com.sergeymikhovich.notes.sync.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.sergeymikhovich.notes.core.common.di.Dispatcher
import com.sergeymikhovich.notes.core.common.di.NoteDispatchers.IO
import com.sergeymikhovich.notes.core.data.Syncronizer
import com.sergeymikhovich.notes.core.datastore.ChangeListVersions
import com.sergeymikhovich.notes.core.datastore.NotesPreferencesDataStore
import com.sergeymikhovich.notes.core.data.repository.NoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

@HiltWorker
internal class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext : Context,
    @Assisted workerParameters: WorkerParameters,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository,
    private val notesPreferencesDataStore: NotesPreferencesDataStore
): CoroutineWorker(appContext, workerParameters), Syncronizer {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val syncSuccessfully = async { noteRepository.sync() }.await()
        if (syncSuccessfully) Result.success() else Result.retry()
    }

    override suspend fun getChangeListVersions(): ChangeListVersions =
        notesPreferencesDataStore.getChangeListVersions()

    override suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions) {
        notesPreferencesDataStore.updateChangeListVersions(update)
    }

    companion object {
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(SyncWorker::class.delegateData())
            .build()
    }
}