package com.sergeymikhovich.notes.core.data

import android.util.Log
import com.sergeymikhovich.notes.core.datastore.ChangeListVersions
import com.sergeymikhovich.notes.core.network.model.NetworkChangeList
import kotlin.coroutines.cancellation.CancellationException

interface Syncronizer {
    suspend fun getChangeListVersions(): ChangeListVersions

    suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions)

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
    versionReader: (ChangeListVersions) -> Int,
    changeListFetcher: suspend (Int) -> List<NetworkChangeList>,
    versionUpdater: ChangeListVersions.(Int) -> ChangeListVersions,
    modelDeleter: suspend (List<String>) -> Unit,
    modelUpdater: suspend (List<String>) -> Unit
) = suspendRunCatching {

    val currentVersion = versionReader(getChangeListVersions())
    val changeList = changeListFetcher(currentVersion)
    if (changeList.isEmpty()) return@suspendRunCatching

    val (deleted, updated) = changeList.partition(NetworkChangeList::isDelete)

    modelDeleter(deleted.map(NetworkChangeList::id))
    modelUpdater(updated.map(NetworkChangeList::id))

    val latestVersion = changeList.last().changeListVersion

    updateChangeListVersions{
        versionUpdater(latestVersion)
    }
}.isSuccess