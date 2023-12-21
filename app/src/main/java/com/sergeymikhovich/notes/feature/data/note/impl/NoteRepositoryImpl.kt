package com.sergeymikhovich.notes.feature.data.note.impl

import com.sergeymikhovich.notes.common.di.context.DispatcherIO
import com.sergeymikhovich.notes.common.di.scope.ApplicationScope
import com.sergeymikhovich.notes.feature.data.note.api.LocalNoteDataSource
import com.sergeymikhovich.notes.feature.domain.note.api.model.NewNote
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.domain.repository.api.NoteRepository
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NoteRepositoryImpl @Inject constructor(
    private val localNoteDataSource: LocalNoteDataSource,
    @DispatcherIO private val context: CoroutineContext
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
interface NoteRepositoryModule {

    @Binds
    @ApplicationScope
    fun bindsRepository(repository: NoteRepositoryImpl): NoteRepository
}