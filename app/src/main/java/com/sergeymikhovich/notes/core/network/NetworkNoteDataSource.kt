package com.sergeymikhovich.notes.core.network

import com.sergeymikhovich.notes.core.network.model.NetworkNote
import kotlinx.coroutines.flow.Flow

interface NetworkNoteDataSource {

    fun observeAll(): Flow<List<NetworkNote>>

    suspend fun getByIds(ids: List<String>): List<NetworkNote>

    suspend fun deleteByIds(ids: List<String>)

    suspend fun upsert(networkNote: NetworkNote)

    suspend fun upsertAll(networkNotes: List<NetworkNote>)
}