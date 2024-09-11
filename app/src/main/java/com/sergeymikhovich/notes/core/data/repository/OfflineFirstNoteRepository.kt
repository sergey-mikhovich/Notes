package com.sergeymikhovich.notes.core.data.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sergeymikhovich.notes.core.common.di.ApplicationScope
import com.sergeymikhovich.notes.core.common.di.Dispatcher
import com.sergeymikhovich.notes.core.common.di.NoteDispatchers.IO
import com.sergeymikhovich.notes.core.data.SyncManager
import com.sergeymikhovich.notes.core.data.Syncronizer
import com.sergeymikhovich.notes.core.data.changeListSync
import com.sergeymikhovich.notes.core.data.mapper.NoteMapper
import com.sergeymikhovich.notes.core.database.dao.ChangeNoteDao
import com.sergeymikhovich.notes.core.database.dao.NoteDao
import com.sergeymikhovich.notes.core.database.model.ChangeNoteEntity
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.core.network.NetworkChangeNoteDataSource
import com.sergeymikhovich.notes.core.network.NetworkNoteDataSource
import com.sergeymikhovich.notes.core.network.getCurrentUserId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineFirstNoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val changeNoteDao: ChangeNoteDao,
    private val networkNoteDataSource: NetworkNoteDataSource,
    private val networkChangeNoteDataSource: NetworkChangeNoteDataSource,
    @Dispatcher(IO) private val context: CoroutineDispatcher,
    @ApplicationScope scope: CoroutineScope,
    private val mapper: NoteMapper,
    private val syncManager: SyncManager
) : NoteRepository {

    init {
        scope.launch {
            remoteSyncListener()
        }
    }

    private suspend fun remoteSyncListener() {
        networkNoteDataSource
            .observeAll(Firebase.auth.getCurrentUserId())
            .collectLatest {
                syncManager.requestSync()
            }
    }

    override fun observeAll(): Flow<List<Note>> =
        noteDao.observeAll(Firebase.auth.getCurrentUserId())
            .map { localNotes -> localNotes.map(mapper.entityToDomainNote) }

    override suspend fun getById(id: String): Note? = withContext(context) {
        noteDao.getById(id, Firebase.auth.getCurrentUserId())?.let(mapper.entityToDomainNote)
    }

    override suspend fun deleteById(id: String) = withContext(context) {
        noteDao.deleteById(id)
        changeNoteDao.upsert(
            ChangeNoteEntity(
                id = id,
                userId = Firebase.auth.getCurrentUserId(),
                lastModifiedTime = System.currentTimeMillis(),
                deleted = true
            )
        )
        syncManager.requestSync()
    }

    override suspend fun upsert(note: Note): Note? = withContext(context) {
        noteDao.upsert(mapper.domainToEntityNote(note))
        changeNoteDao.upsert(
            ChangeNoteEntity(
                id = note.id,
                userId = note.userId,
                lastModifiedTime = System.currentTimeMillis(),
                deleted = false
            )
        )
        syncManager.requestSync()
        getById(note.id)
    }

    override suspend fun syncWith(syncronizer: Syncronizer): Boolean =
        syncronizer.changeListSync(
            localSyncTimeReader = {
                val lastChangeNote = changeNoteDao
                    .getLastChangeNote(Firebase.auth.getCurrentUserId()) ?: return@changeListSync -1
                mapper.entityToDomainChangeNote(lastChangeNote).lastModifiedTime
            },
            remoteSyncTimeReader = {
                val lastChangeNote = networkChangeNoteDataSource
                    .getLastChangeNote(Firebase.auth.getCurrentUserId()) ?: return@changeListSync -1
                mapper.networkToDomainChangeNote(lastChangeNote).lastModifiedTime
            },
            localChangeListFetcher = { currentVersion ->
                changeNoteDao.getChangeNotesAfter(currentVersion, Firebase.auth.getCurrentUserId())
                    .map(mapper.entityToDomainChangeNote)
            },
            remoteChangeListFetcher = { currentVersion ->
                networkChangeNoteDataSource.getChangeNotesAfter(currentVersion, Firebase.auth.getCurrentUserId())
                    .map(mapper.networkToDomainChangeNote)
            },
            localModelDeleter = { idsToDelete ->
                noteDao.deleteByIds(idsToDelete)
            },
            remoteModelDeleter = { idsToDelete ->
                networkNoteDataSource.deleteByIds(idsToDelete)
            },
            localModelUpdater = { changedIds ->
                val networkNotes = networkNoteDataSource.getByIds(changedIds, Firebase.auth.getCurrentUserId())
                noteDao.upsertAll(networkNotes.map(mapper.networkToEntityNote))
            },
            remoteModelUpdater = { changedIds ->
                val localNotes = noteDao.getByIds(changedIds, Firebase.auth.getCurrentUserId())
                networkNoteDataSource.upsertAll(localNotes.map(mapper.entityToNetworkNote))
            },
            localChangeListUpdater = { lastLocalSyncTime ->
                val networkChangeNotes = networkChangeNoteDataSource
                    .getChangeNotesAfter(lastLocalSyncTime, Firebase.auth.getCurrentUserId())
                changeNoteDao.upsertAll(networkChangeNotes.map(mapper.networkToEntityChangeNote))
            },
            remoteChangeListUpdater = { lastRemoteSyncTime ->
                val localChangeNotes = changeNoteDao
                    .getChangeNotesAfter(lastRemoteSyncTime, Firebase.auth.getCurrentUserId())
                networkChangeNoteDataSource.upsertAll(localChangeNotes.map(mapper.entityToNetworkChangeNote))
            }
        )
}