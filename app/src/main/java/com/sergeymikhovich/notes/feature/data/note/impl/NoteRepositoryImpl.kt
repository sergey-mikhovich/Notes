package com.sergeymikhovich.notes.feature.data.note.impl

import com.sergeymikhovich.notes.common.di.DispatcherIO
import com.sergeymikhovich.notes.feature.data.note.api.LocalNoteDataSource
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class NoteRepositoryImpl @Inject constructor(
    private val localNoteDataSource: LocalNoteDataSource,
    @DispatcherIO private val context: CoroutineDispatcher
) : NoteRepository {

    override suspend fun getAll(): List<Note> = withContext(context) {
        localNoteDataSource.getAll()
    }

    override fun observeAll(): Flow<List<Note>> = localNoteDataSource.observeAll()

    override suspend fun getById(id: String): Note? = withContext(context) {
        localNoteDataSource.getById(id)
    }

    override suspend fun delete(id: String) = withContext(context) {
        localNoteDataSource.delete(id)
    }

    override suspend fun add(note: Note): Note? = withContext(context) {
        localNoteDataSource.add(note)
    }

    override suspend fun update(note: Note) = withContext(context) {
        localNoteDataSource.update(note)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface NoteRepositoryModule {

    @Binds
    @Singleton
    fun bindsRepository(repository: NoteRepositoryImpl): NoteRepository
}