package com.sergeymikhovich.notes.feature.data.note.impl

import com.sergeymikhovich.notes.common.mapping.Mapper
import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteEntity
import com.sergeymikhovich.notes.feature.data.note.impl.remote.NoteFirebase
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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

    val networkFirebaseToDomain by lazy {
        Mapper<NoteFirebase, Note> { network ->
            Note(
                id = network.id,
                title = network.title,
                description = network.description
            )
        }
    }

    val domainToNetworkFirebase by lazy {
        Mapper<Note, NoteFirebase> { domain ->
            NoteFirebase(
                id = domain.id,
                title = domain.title,
                description = domain.description
            )
        }
    }

    val networkFirebaseToEntity by lazy {
        Mapper<NoteFirebase, NoteEntity> { network ->
            NoteEntity(
                id = network.id,
                title = network.title,
                description = network.description
            )
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
class NoteMapperModule {

    @Provides
    fun provideNoteMapper(): NoteMapper = NoteMapper()
}