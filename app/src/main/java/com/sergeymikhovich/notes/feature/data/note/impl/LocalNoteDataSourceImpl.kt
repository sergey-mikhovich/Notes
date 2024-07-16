package com.sergeymikhovich.notes.feature.data.note.impl

import com.sergeymikhovich.notes.feature.data.note.api.LocalNoteDataSource
import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteDao
import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class LocalNoteDataSourceImpl @Inject constructor(
    private val dao: NoteDao
) : LocalNoteDataSource {

    override suspend fun getAll(): List<NoteEntity> {
        return dao.getAll()
    }

    override fun observeAll(): Flow<List<NoteEntity>> {
        return dao.observeAll()
    }

    override suspend fun getById(id: String): NoteEntity? {
        return dao.getById(id)
    }

    override suspend fun deleteById(id: String) {
        dao.deleteById(id)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun upsert(noteEntity: NoteEntity): NoteEntity? {
        dao.upsert(noteEntity)
        return getById(noteEntity.id)
    }

    override suspend fun upsertAll(noteEntities: List<NoteEntity>) {
        dao.upsertAll(noteEntities)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {

    @Binds
    @Singleton
    fun bindLocalDataSource(localNoteDataSource: LocalNoteDataSourceImpl): LocalNoteDataSource
}