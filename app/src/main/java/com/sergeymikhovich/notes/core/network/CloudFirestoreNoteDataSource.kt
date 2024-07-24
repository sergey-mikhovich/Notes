package com.sergeymikhovich.notes.core.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.sergeymikhovich.notes.core.network.model.NetworkNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CloudFirestoreNoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : NetworkNoteDataSource {

    override fun observeAll(): Flow<List<NetworkNote>> =
        firestore
            .collection(NetworkNote.COLLECTION_NAME)
            .snapshots().map { querySnapshot ->
                querySnapshot.map {
                    it.toObject<NetworkNote>()
                }
            }

    override suspend fun getByIds(ids: List<String>): List<NetworkNote> {
        if (ids.isEmpty()) return emptyList()

        val querySnapshot = firestore
            .collection(NetworkNote.COLLECTION_NAME)
            .whereIn(NetworkNote.ID, ids)
            .get()
            .await()

        return querySnapshot.map { it.toObject<NetworkNote>() }
    }

    override suspend fun deleteByIds(ids: List<String>) {
        if (ids.isEmpty()) return

        firestore.runBatch { batch ->
            for (id in ids) {
                batch.delete(
                    firestore
                        .collection(NetworkNote.COLLECTION_NAME)
                        .document(id)
                )
            }
        }
            .await()
    }

    override suspend fun upsert(networkNote: NetworkNote) {
        val note = hashMapOf(
            NetworkNote.ID to networkNote.id,
            NetworkNote.TITLE to networkNote.title,
            NetworkNote.DESCRIPTION to networkNote.description
        )

        firestore
            .collection(NetworkNote.COLLECTION_NAME)
            .document(networkNote.id)
            .set(note)
            .await()
    }

    override suspend fun upsertAll(networkNotes: List<NetworkNote>) {
        if (networkNotes.isEmpty()) return

        firestore.runBatch { batch ->
            for (networkNote in networkNotes) {
                val note = hashMapOf(
                    NetworkNote.ID to networkNote.id,
                    NetworkNote.TITLE to networkNote.title,
                    NetworkNote.DESCRIPTION to networkNote.description
                )

                batch.set(
                    firestore
                        .collection(NetworkNote.COLLECTION_NAME)
                        .document(networkNote.id),
                    note
                )
            }
        }.await()
    }
}