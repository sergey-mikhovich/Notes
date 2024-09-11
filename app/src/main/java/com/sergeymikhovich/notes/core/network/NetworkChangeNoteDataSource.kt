package com.sergeymikhovich.notes.core.network

import com.sergeymikhovich.notes.core.network.model.NetworkChangeNote

interface NetworkChangeNoteDataSource {

    suspend fun getChangeNotesAfter(time: Long, userId: String): List<NetworkChangeNote>

    suspend fun getLastChangeNote(userId: String): NetworkChangeNote?

    suspend fun upsert(networkChangeNote: NetworkChangeNote)

    suspend fun upsertAll(networkChangeNotes: List<NetworkChangeNote>)
}