package com.sergeymikhovich.notes.feature.data.note.impl

import android.util.Log
import com.sergeymikhovich.notes.common.di.DispatcherIO
import com.sergeymikhovich.notes.feature.data.note.api.LocalNoteDataSource
import com.sergeymikhovich.notes.feature.data.note.api.RemoteNoteDataSource
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class NoteRepositoryImpl @Inject constructor(
    private val localNoteDataSource: LocalNoteDataSource,
    private val remoteNoteDataSource: RemoteNoteDataSource,
    @DispatcherIO private val context: CoroutineDispatcher,
    private val mapper: NoteMapper
) : NoteRepository {

    override suspend fun getAll(): List<Note> = withContext(context) {
        localNoteDataSource.getAll().map(mapper.entityToDomain)
    }

    override fun observeAll(): Flow<List<Note>> {
        val localNotes = localNoteDataSource.observeAll().map { localNotes ->
            Log.d("NoteRepository", "localNotes = $localNotes")
            localNotes.map(mapper.entityToDomain)
        }

        val remoteNotes = remoteNoteDataSource.observeAll().map { remoteNotes ->
            Log.d("NoteRepository", "remoteNotes = $remoteNotes")
            remoteNotes.map(mapper.networkFirebaseToDomain)
        }

        return remoteNotes
    }

    override suspend fun getById(id: String): Note? = withContext(context) {
        return@withContext localNoteDataSource.getById(id)?.let(mapper.entityToDomain)
    }

    override suspend fun deleteById(id: String) = withContext(context) {
        localNoteDataSource.deleteById(id)
        remoteNoteDataSource.deleteById(id)
    }

    override suspend fun upsert(note: Note): Note? = withContext(context) {
        localNoteDataSource.upsert(mapper.domainToEntity(note))
        remoteNoteDataSource.upsert(mapper.domainToNetworkFirebase(note))
        return@withContext getById(note.id)

//        return@withContext remoteNoteDataSource.add(mapper.domainToNetworkFirebase(note))?.let { noteFirebase ->
//            mapper.networkFirebaseToDomain(noteFirebase)
//        }
    }

//    override suspend fun update(note: Note) = withContext(context) {
//        //localNoteDataSource.update(mapper.domainToEntity(note))
//
//        localNoteDataSource.upsert(mapper.domainToEntity(note))
//        remoteNoteDataSource.update(mapper.domainToNetworkFirebase(note))
//    }

    override suspend fun refresh() = withContext(context) {
        val notes = remoteNoteDataSource.loadAll()
        localNoteDataSource.deleteAll()
        localNoteDataSource.upsertAll(notes.map(mapper.networkFirebaseToEntity))
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface NoteRepositoryModule {

    @Binds
    @Singleton
    fun bindsRepository(repository: NoteRepositoryImpl): NoteRepository
}