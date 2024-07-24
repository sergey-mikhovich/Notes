package com.sergeymikhovich.notes.core.data

import android.util.Log
import com.sergeymikhovich.notes.core.database.model.ChangeNoteEntity
import com.sergeymikhovich.notes.core.model.ChangeNote
import com.sergeymikhovich.notes.core.network.model.NetworkChangeNote
import kotlin.coroutines.cancellation.CancellationException

interface Syncronizer {
    suspend fun Syncable.sync() = this@sync.syncWith(this@Syncronizer)
}

interface Syncable {
    suspend fun syncWith(syncronizer: Syncronizer): Boolean
}

private suspend fun <T> suspendRunCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Log.i(
        "suspendRunCatching",
        "Failed to evaluate a suspendRunCatchingBlock. Returning failure result",
        exception
    )
    Result.failure(exception)
}

suspend fun Syncronizer.changeListSync(
    localSyncTimeReader: suspend () -> Long,
    remoteSyncTimeReader: suspend () -> Long,
    localChangeListFetcher: suspend (Long) -> List<ChangeNote>,
    remoteChangeListFetcher: suspend (Long) -> List<ChangeNote>,
    localModelDeleter: suspend (List<String>) -> Unit,
    remoteModelDeleter: suspend (List<String>) -> Unit,
    localModelUpdater: suspend (List<String>) -> Unit,
    remoteModelUpdater: suspend (List<String>) -> Unit,
    localChangeListUpdater: suspend (Long) -> Unit,
    remoteChangeListUpdater: suspend (Long) -> Unit
) = suspendRunCatching {

    val lastLocalSyncTime = localSyncTimeReader()
    val lastRemoteSyncTime = remoteSyncTimeReader()

    val syncWithRemote = lastRemoteSyncTime > lastLocalSyncTime

    val changeList =
        if (syncWithRemote)
            remoteChangeListFetcher(lastLocalSyncTime)
        else
            localChangeListFetcher(lastRemoteSyncTime)

    if (changeList.isEmpty()) return@suspendRunCatching

    val (deleted, updated) = changeList.partition(ChangeNote::deleted)

    if (syncWithRemote) {
        localModelDeleter(deleted.map(ChangeNote::id))
        localModelUpdater(updated.map(ChangeNote::id))
        localChangeListUpdater(lastLocalSyncTime)
    } else {
        remoteModelDeleter(deleted.map(ChangeNote::id))
        remoteModelUpdater(updated.map(ChangeNote::id))
        remoteChangeListUpdater(lastRemoteSyncTime)
    }
}.isSuccess