package com.sergeymikhovich.notes.core.data.mapper

import com.sergeymikhovich.notes.core.common.mapping.Mapper
import com.sergeymikhovich.notes.core.database.model.ChangeNoteEntity
import com.sergeymikhovich.notes.core.database.model.NoteEntity
import com.sergeymikhovich.notes.core.model.ChangeNote
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.core.network.model.NetworkChangeNote
import com.sergeymikhovich.notes.core.network.model.NetworkNote

class NoteMapper {

    val entityToDomainNote by lazy {
        Mapper<NoteEntity, Note> { entity ->
            Note(
                id = entity.id,
                title = entity.title,
                description = entity.description
            )
        }
    }

    val domainToEntityNote by lazy {
        Mapper<Note, NoteEntity> { domain ->
            NoteEntity(
                id = domain.id,
                title = domain.title,
                description = domain.description
            )
        }
    }

    val networkToEntityNote by lazy {
        Mapper<NetworkNote, NoteEntity> { network ->
            NoteEntity(
                id = network.id,
                title = network.title,
                description = network.description
            )
        }
    }

    val entityToNetworkNote by lazy {
        Mapper<NoteEntity, NetworkNote> { entiry ->
            NetworkNote(
                id = entiry.id,
                title = entiry.title,
                description = entiry.description
            )
        }
    }

    val entityToDomainChangeNote by lazy {
        Mapper<ChangeNoteEntity, ChangeNote> { entity ->
            ChangeNote(
                id = entity.id,
                lastModifiedTime = entity.lastModifiedTime,
                deleted = entity.deleted
            )
        }
    }

    val entityToNetworkChangeNote by lazy {
        Mapper<ChangeNoteEntity, NetworkChangeNote> { entity ->
            NetworkChangeNote(
                id = entity.id,
                lastModifiedTime = entity.lastModifiedTime,
                deleted = entity.deleted
            )
        }
    }

    val networkToDomainChangeNote by lazy {
        Mapper<NetworkChangeNote, ChangeNote> { network ->
            ChangeNote(
                id = network.id,
                lastModifiedTime = network.lastModifiedTime,
                deleted = network.deleted
            )
        }
    }

    val networkToEntityChangeNote by lazy {
        Mapper<NetworkChangeNote, ChangeNoteEntity> { network ->
            ChangeNoteEntity(
                id = network.id,
                lastModifiedTime = network.lastModifiedTime,
                deleted = network.deleted
            )
        }
    }
}