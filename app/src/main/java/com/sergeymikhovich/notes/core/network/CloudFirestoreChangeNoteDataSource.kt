package com.sergeymikhovich.notes.core.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.sergeymikhovich.notes.core.network.model.NetworkChangeNote
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CloudFirestoreChangeNoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
): NetworkChangeNoteDataSource {



    override suspend fun getChangeNotesAfter(time: Long): List<NetworkChangeNote> {
        val querySnapshot = firestore
            .collection(NetworkChangeNote.COLLECTION_NAME)
            .whereGreaterThan(NetworkChangeNote.LAST_MODIFIED_TIME, time)
            .get()
            .await()

        return querySnapshot.map { it.toObject<NetworkChangeNote>() }
    }

    override suspend fun getLastChangeNote(): NetworkChangeNote {
        val querySnapshot = firestore
            .collection(NetworkChangeNote.COLLECTION_NAME)
            .orderBy(NetworkChangeNote.LAST_MODIFIED_TIME, Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        return querySnapshot
            .map { it.toObject<NetworkChangeNote>() }
            .lastOrNull()
            ?: NetworkChangeNote()
    }

    override suspend fun upsert(networkChangeNote: NetworkChangeNote) {
        val changeNote = hashMapOf(
            NetworkChangeNote.ID to networkChangeNote.id,
            NetworkChangeNote.LAST_MODIFIED_TIME to networkChangeNote.lastModifiedTime,
            NetworkChangeNote.DELETED to networkChangeNote.deleted
        )

        firestore
            .collection(NetworkChangeNote.COLLECTION_NAME)
            .document(networkChangeNote.id)
            .set(changeNote)
            .await()
    }

    override suspend fun upsertAll(networkChangeNotes: List<NetworkChangeNote>) {
        firestore.runBatch { batch ->
            for (networkChangeNote in networkChangeNotes) {
                val changeNote = hashMapOf(
                    NetworkChangeNote.ID to networkChangeNote.id,
                    NetworkChangeNote.LAST_MODIFIED_TIME to networkChangeNote.lastModifiedTime,
                    NetworkChangeNote.DELETED to networkChangeNote.deleted
                )

                batch.set(
                    firestore
                        .collection(NetworkChangeNote.COLLECTION_NAME)
                        .document(networkChangeNote.id),
                    changeNote
                )
            }
        }.await()
    }
}