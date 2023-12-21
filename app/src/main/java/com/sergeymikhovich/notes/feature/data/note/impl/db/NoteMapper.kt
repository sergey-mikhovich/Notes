package com.sergeymikhovich.notes.feature.data.note.impl.db

import com.sergeymikhovich.notes.common.di.scope.ApplicationScope
import com.sergeymikhovich.notes.common.mapping.Mapper
import com.sergeymikhovich.notes.feature.domain.note.api.model.NewNote
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import dagger.Module
import dagger.Provides

class NoteMapper {

    val entityToDomain by lazy {
        Mapper<NoteEntity, Note> { entity ->
            Note(
                id = entity.id,
                title = entity.title,
                description = entity.description
            )
        }
    }

    val domainToEntity by lazy {
        Mapper<Note, NoteEntity> { domain ->
            NoteEntity(
                id = domain.id,
                title = domain.title,
                description = domain.description
            )
        }
    }

    val newDomainToEntity by lazy {
        Mapper<NewNote, NoteEntity> { newDomain ->
            NoteEntity(
                id = 0,
                title = newDomain.title,
                description = newDomain.description
            )
        }
    }
}

@Module
class NoteMapperModule {

    @Provides
    @ApplicationScope
    fun provideNoteMapper(): NoteMapper = NoteMapper()
}