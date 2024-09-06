package com.sergeymikhovich.notes.core.network

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.sergeymikhovich.notes.core.network.model.NetworkNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CloudFirestoreNoteDataSource @Inject constructor(): NetworkNoteDataSource {

    override fun observeAll(): Flow<List<NetworkNote>> =
        Firebase.firestore
            .collection(NetworkNote.COLLECTION_NAME)
            .snapshots().map { querySnapshot ->
                querySnapshot.map {
                    it.toObject<NetworkNote>()
                }
            }

    override suspend fun getByIds(ids: List<String>): List<NetworkNote> {
        if (ids.isEmpty()) return emptyList()

        val querySnapshot = Firebase.firestore
            .collection(NetworkNote.COLLECTION_NAME)
            .whereIn(NetworkNote.ID, ids)
            .get()
            .await()

        return querySnapshot.map { it.toObject<NetworkNote>() }
    }

    override suspend fun deleteByIds(ids: List<String>) {
        if (ids.isEmpty()) return

        Firebase.firestore.runBatch { writeBatch ->
            for (id in ids) {
                writeBatch.delete(
                    Firebase.firestore
                        .collection(NetworkNote.COLLECTION_NAME)
                        .document(id)
                )
//                Firebase.firestore
//                    .collection(NetworkNote.COLLECTION_NAME)
//                    //.whereEqualTo(NetworkNote.USER_ID, Firebase.auth.getCurrentUserId())
//                    .whereEqualTo(NetworkNote.ID, id)
//                    .get()
//                    .continueWith { task ->
//                        task.result.forEach { snapshot ->
//                            writeBatch.delete(snapshot.reference)
//                        }
//                    }
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

//        Firebase.firestore
//            .collection(NetworkNote.COLLECTION_NAME)
//            .whereEqualTo(NetworkNote.USER_ID, Firebase.auth.getCurrentUserId())
//            .whereEqualTo(NetworkNote.ID, networkNote.id)
//            .get()
//            .continueWith { snapshotTask ->
//                if (snapshotTask.result.isEmpty) {
                    Firebase.firestore
                        .collection(NetworkNote.COLLECTION_NAME)
                        .document(networkNote.id)
                        .set(note)
//                } else {
//                    snapshotTask.result.forEach { snapshot ->
//                        snapshot.reference.set(note)
//                    }
//                }
//            }
            .await()
    }

    override suspend fun upsertAll(networkNotes: List<NetworkNote>) {
        if (networkNotes.isEmpty()) return

        Firebase.firestore.runBatch { writeBatch ->
            for (networkNote in networkNotes) {
                val note = hashMapOf(
                    NetworkNote.ID to networkNote.id,
                    NetworkNote.TITLE to networkNote.title,
                    NetworkNote.DESCRIPTION to networkNote.description
                )

//                Firebase.firestore
//                    .collection(NetworkNote.COLLECTION_NAME)
//                    .whereEqualTo(NetworkNote.USER_ID, Firebase.auth.getCurrentUserId())
//                    .whereEqualTo(NetworkNote.ID, networkNote.id)
//                    .get()
//                    .continueWith { snapshotTask ->
//                        Log.d("CloudFirestoreNoteDataSource", "Snapshot task result = ${snapshotTask.result.size()}")
//                        if (snapshotTask.result.isEmpty) {
                            writeBatch.set(
                                Firebase.firestore
                                    .collection(NetworkNote.COLLECTION_NAME)
                                    .document(networkNote.id),
                                note
                            )
//                        } else {
//                            snapshotTask.result.forEach { snapshot ->
//                                writeBatch.set(snapshot.reference, note)
//                            }
//                        }
//                    }
            }
        }.await()
    }
}