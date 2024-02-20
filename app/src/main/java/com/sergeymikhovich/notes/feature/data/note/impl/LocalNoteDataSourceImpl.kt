package com.sergeymikhovich.notes.feature.data.note.impl

import com.sergeymikhovich.notes.feature.data.note.api.LocalNoteDataSource
import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteDao
import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteMapper
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

class LocalNoteDataSourceImpl @Inject constructor(
    private val dao: NoteDao,
    private val mapper: NoteMapper
) : LocalNoteDataSource {

    override suspend fun getAll(): List<Note> {
        return dao.getAll().map(mapper.entityToDomain)
    }

    override fun observeAll(): Flow<List<Note>> {
        return dao.observeAll().map { entities ->
            entities.map(mapper.entityToDomain)
        }
    }

    override suspend fun getById(id: String): Note? {
        return dao.getById(id)?.let(mapper.entityToDomain)
    }

    override suspend fun delete(id: String) {
        dao.delete(id)
    }

    override suspend fun add(note: Note): Note? {
        dao.add(mapper.domainToEntity(note))
        return getById(note.id)
    }

    override suspend fun update(note: Note) {
        dao.update(mapper.domainToEntity(note))
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {

    @Binds
    @Singleton
    fun bindLocalDataSource(localNoteDataSource: LocalNoteDataSourceImpl): LocalNoteDataSource
}