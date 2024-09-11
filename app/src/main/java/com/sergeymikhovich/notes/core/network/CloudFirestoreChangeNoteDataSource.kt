package com.sergeymikhovich.notes.core.network

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.sergeymikhovich.notes.core.network.model.NetworkChangeNote
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CloudFirestoreChangeNoteDataSource @Inject constructor(): NetworkChangeNoteDataSource {

    override suspend fun getChangeNotesAfter(time: Long, userId: String): List<NetworkChangeNote> {
        val querySnapshot = Firebase.firestore
            .collection(NetworkChangeNote.COLLECTION_NAME)
            .whereGreaterThan(NetworkChangeNote.LAST_MODIFIED_TIME, time)
            .whereEqualTo(NetworkChangeNote.USER_ID, userId)
            .get()
            .await()

        return querySnapshot.map { it.toObject<NetworkChangeNote>() }
    }

    override suspend fun getLastChangeNote(userId: String): NetworkChangeNote? {
        val querySnapshot = Firebase.firestore
            .collection(NetworkChangeNote.COLLECTION_NAME)
            .whereEqualTo(NetworkChangeNote.USER_ID, userId)
            .orderBy(NetworkChangeNote.LAST_MODIFIED_TIME, Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        return querySnapshot.map { it.toObject<NetworkChangeNote>() }.lastOrNull()
    }

    override suspend fun upsert(networkChangeNote: NetworkChangeNote) {
        val changeNote = hashMapOf(
            NetworkChangeNote.ID to networkChangeNote.id,
            NetworkChangeNote.USER_ID to networkChangeNote.userId,
            NetworkChangeNote.LAST_MODIFIED_TIME to networkChangeNote.lastModifiedTime,
            NetworkChangeNote.DELETED to networkChangeNote.deleted
        )

//        Firebase.firestore
//            .collection(NetworkChangeNote.COLLECTION_NAME)
//            .whereEqualTo(NetworkChangeNote.USER_ID, Firebase.auth.getCurrentUserId())
//            .whereEqualTo(NetworkChangeNote.ID, networkChangeNote.id)
//            .get()
//            .continueWith { snapshotTask ->
//                if (snapshotTask.result.isEmpty) {
                    Firebase.firestore
                        .collection(NetworkChangeNote.COLLECTION_NAME)
                        .document(networkChangeNote.id)
                        .set(changeNote)
//                } else {
//                    snapshotTask.result.forEach { snapshot ->
//                        snapshot.reference.set(changeNote)
//                    }
//                }
//            }
            .await()
    }

    override suspend fun upsertAll(networkChangeNotes: List<NetworkChangeNote>) {
        Firebase.firestore.runBatch { writeBatch ->
            for (networkChangeNote in networkChangeNotes) {
                val changeNote = hashMapOf(
                    NetworkChangeNote.ID to networkChangeNote.id,
                    NetworkChangeNote.USER_ID to networkChangeNote.userId,
                    NetworkChangeNote.LAST_MODIFIED_TIME to networkChangeNote.lastModifiedTime,
                    NetworkChangeNote.DELETED to networkChangeNote.deleted
                )

//                Firebase.firestore
//                    .collection(NetworkChangeNote.COLLECTION_NAME)
//                    .whereEqualTo(NetworkChangeNote.USER_ID, Firebase.auth.getCurrentUserId())
//                    .whereEqualTo(NetworkChangeNote.ID, networkChangeNote.id)
//                    .get()
//                    .continueWith { snapshotTask ->
//                        if (snapshotTask.result.isEmpty) {
                            writeBatch.set(
                                Firebase.firestore
                                    .collection(NetworkChangeNote.COLLECTION_NAME)
                                    .document(networkChangeNote.id),
                                changeNote
                            )
//                        } else {
//                            snapshotTask.result.forEach { snapshot ->
//                                writeBatch.set(snapshot.reference, changeNote)
//                            }
//                        }
//                    }
            }
        }.await()
    }
}