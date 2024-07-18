package com.sergeymikhovich.notes.core.network

import com.sergeymikhovich.notes.core.network.model.NetworkChangeList
import com.sergeymikhovich.notes.core.network.model.NetworkNote
import kotlinx.coroutines.flow.Flow

interface NetworkNoteDataSource {

    fun observeAll(): Flow<List<NetworkNote>>

    suspend fun loadAll(): List<NetworkNote>

    suspend fun loadByIds(ids: List<String>): List<NetworkNote>

    suspend fun deleteById(id: String)

    suspend fun upsert(networkNote: NetworkNote)

    suspend fun upsert(networkChangeList: NetworkChangeList)

    suspend fun getChangeList(after: Int): List<NetworkChangeList>
}