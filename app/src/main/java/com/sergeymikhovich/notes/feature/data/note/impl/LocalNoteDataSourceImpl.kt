package com.sergeymikhovich.notes.feature.data.note.impl

import com.sergeymikhovich.notes.common.di.scope.ApplicationScope
import com.sergeymikhovich.notes.feature.data.note.api.LocalNoteDataSource
import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteDao
import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteMapper
import com.sergeymikhovich.notes.feature.domain.note.api.model.NewNote
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class LocalNoteDataSourceImpl @Inject constructor(
    private val dao: NoteDao,
    private val mapper: NoteMapper
) : LocalNoteDataSource {

    override suspend fun getAll(): List<Note> {
        return dao.getAll().map(mapper.entityToDomain)
    }

    override suspend fun getById(id: Long): Note? {
        return dao.getById(id)?.let(mapper.entityToDomain)
    }

    override suspend fun delete(note: Note) {
        dao.delete(mapper.domainToEntity(note))
    }

    override suspend fun add(newNote: NewNote): Note? {
        val newNoteId = dao.add(mapper.newDomainToEntity(newNote))
        return getById(newNoteId)
    }

    override suspend fun update(note: Note) {
        dao.update(mapper.domainToEntity(note))
    }
}

@Module
interface LocalDataSourceModule {

    @Binds
    @ApplicationScope
    fun bindLocalDataSource(localNoteDataSource: LocalNoteDataSourceImpl): LocalNoteDataSource
}