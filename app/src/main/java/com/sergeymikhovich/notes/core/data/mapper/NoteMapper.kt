package com.sergeymikhovich.notes.core.data.mapper

import com.sergeymikhovich.notes.core.common.mapping.Mapper
import com.sergeymikhovich.notes.core.database.model.NoteEntity
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.core.network.model.NetworkNote

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
        Mapper<NetworkNote, Note> { network ->
            Note(
                id = network.id,
                title = network.title,
                description = network.description
            )
        }
    }

    val domainToNetworkFirebase by lazy {
        Mapper<Note, NetworkNote> { domain ->
            NetworkNote(
                id = domain.id,
                title = domain.title,
                description = domain.description
            )
        }
    }

    val networkFirebaseToEntity by lazy {
        Mapper<NetworkNote, NoteEntity> { network ->
            NoteEntity(
                id = network.id,
                title = network.title,
                description = network.description
            )
        }
    }
}