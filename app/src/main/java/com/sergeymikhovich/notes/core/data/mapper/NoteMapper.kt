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
                userId = entity.userId,
                title = entity.title,
                description = entity.description
            )
        }
    }

    val domainToEntityNote by lazy {
        Mapper<Note, NoteEntity> { domain ->
            NoteEntity(
                id = domain.id,
                userId = domain.userId,
                title = domain.title,
                description = domain.description
            )
        }
    }

    val networkToEntityNote by lazy {
        Mapper<NetworkNote, NoteEntity> { network ->
            NoteEntity(
                id = network.id,
                userId = network.userId,
                title = network.title,
                description = network.description
            )
        }
    }

    val entityToNetworkNote by lazy {
        Mapper<NoteEntity, NetworkNote> { entity ->
            NetworkNote(
                id = entity.id,
                userId = entity.userId,
                title = entity.title,
                description = entity.description
            )
        }
    }

    val entityToDomainChangeNote by lazy {
        Mapper<ChangeNoteEntity, ChangeNote> { entity ->
            ChangeNote(
                id = entity.id,
                userId = entity.userId,
                lastModifiedTime = entity.lastModifiedTime,
                deleted = entity.deleted
            )
        }
    }

    val entityToNetworkChangeNote by lazy {
        Mapper<ChangeNoteEntity, NetworkChangeNote> { entity ->
            NetworkChangeNote(
                id = entity.id,
                userId = entity.userId,
                lastModifiedTime = entity.lastModifiedTime,
                deleted = entity.deleted
            )
        }
    }

    val networkToDomainChangeNote by lazy {
        Mapper<NetworkChangeNote, ChangeNote> { network ->
            ChangeNote(
                id = network.id,
                userId = network.userId,
                lastModifiedTime = network.lastModifiedTime,
                deleted = network.deleted
            )
        }
    }

    val networkToEntityChangeNote by lazy {
        Mapper<NetworkChangeNote, ChangeNoteEntity> { network ->
            ChangeNoteEntity(
                id = network.id,
                userId = network.userId,
                lastModifiedTime = network.lastModifiedTime,
                deleted = network.deleted
            )
        }
    }
}