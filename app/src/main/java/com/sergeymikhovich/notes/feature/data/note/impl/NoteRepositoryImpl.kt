package com.sergeymikhovich.notes.feature.data.note.impl

import com.sergeymikhovich.notes.common.di.DispatcherIO
import com.sergeymikhovich.notes.feature.data.note.api.LocalNoteDataSource
import com.sergeymikhovich.notes.feature.domain.note.api.model.NewNote
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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

    override suspend fun getById(id: Long): Note? = withContext(context) {
        localNoteDataSource.getById(id)
    }

    override suspend fun delete(note: Note) = withContext(context) {
        localNoteDataSource.delete(note)
    }

    override suspend fun add(newNote: NewNote): Note? = withContext(context) {
        localNoteDataSource.add(newNote)
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