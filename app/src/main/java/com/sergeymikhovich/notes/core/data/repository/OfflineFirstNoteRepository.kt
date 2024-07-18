package com.sergeymikhovich.notes.core.data.repository

import com.sergeymikhovich.notes.core.common.di.Dispatcher
import com.sergeymikhovich.notes.core.common.di.NoteDispatchers.IO
import com.sergeymikhovich.notes.core.data.Syncronizer
import com.sergeymikhovich.notes.core.data.changeListSync
import com.sergeymikhovich.notes.core.network.NetworkNoteDataSource
import com.sergeymikhovich.notes.core.data.mapper.NoteMapper
import com.sergeymikhovich.notes.core.database.dao.NoteDao
import com.sergeymikhovich.notes.core.datastore.ChangeListVersions
import com.sergeymikhovich.notes.core.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineFirstNoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val networkNoteDataSource: NetworkNoteDataSource,
    @Dispatcher(IO) private val context: CoroutineDispatcher,
    private val mapper: NoteMapper
) : NoteRepository {

    override fun observeAll(): Flow<List<Note>> =
        noteDao.observeAll()
            .map { localNotes -> localNotes.map(mapper.entityToDomain) }

    override suspend fun getById(id: String): Note? = withContext(context) {
        noteDao.getById(id)?.let(mapper.entityToDomain)
    }

    override suspend fun deleteById(id: String) = withContext(context) {
        noteDao.deleteById(id)
    }

    override suspend fun upsert(note: Note): Note? = withContext(context) {
        noteDao.upsert(mapper.domainToEntity(note))
        getById(note.id)
    }

    override suspend fun syncWith(syncronizer: Syncronizer): Boolean =
        syncronizer.changeListSync(
            versionReader = ChangeListVersions::noteListVersion,
            changeListFetcher = { currentVersion ->
                networkNoteDataSource.getChangeList(currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(noteListVersion = latestVersion)
            },
            modelDeleter = noteDao::deleteByIds,
            modelUpdater = { changedIds ->
                val networkNotes = networkNoteDataSource.loadByIds(changedIds)
                noteDao.upsertAll(networkNotes.map(mapper.networkFirebaseToEntity))
            }
        )
}