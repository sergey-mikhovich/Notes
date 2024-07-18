package com.sergeymikhovich.notes.core.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.sergeymikhovich.notes.core.network.model.NetworkChangeList
import com.sergeymikhovich.notes.core.network.model.NetworkNote
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

class CloudFirestoreNoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : NetworkNoteDataSource {

    override fun observeAll(): Flow<List<NetworkNote>> {
        return callbackFlow {
            firestore
                .collection(NetworkNote.COLLECTION_NAME)
                .addSnapshotListener { snapshot, _ ->
                    if (snapshot != null && !snapshot.isEmpty) {
                        trySend(snapshot.map { it.toObject<NetworkNote>() })
                    }
                }
            awaitClose {  }
        }
    }

    override suspend fun loadAll(): List<NetworkNote> {
        val querySnapshot = firestore
            .collection(NetworkNote.COLLECTION_NAME)
            .get()
            .await()

        return querySnapshot.map { it.toObject<NetworkNote>() }
    }

    override suspend fun loadByIds(ids: List<String>): List<NetworkNote> {
        val querySnapshot = firestore
            .collection(NetworkNote.COLLECTION_NAME)
            .whereIn(NetworkNote.ID, ids)
            .get()
            .await()

        return querySnapshot.map { it.toObject<NetworkNote>() }
    }

    override suspend fun deleteById(id: String) {
        firestore
            .collection(NetworkNote.COLLECTION_NAME)
            .document(id)
            .delete()
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

    override suspend fun upsert(networkChangeList: NetworkChangeList) {
        val changeList = hashMapOf(
            NetworkChangeList.ID to networkChangeList.id,
            NetworkChangeList.CHANGE_LIST_VERSION to networkChangeList.changeListVersion,
            NetworkChangeList.IS_DELETE to networkChangeList.isDelete
        )

        firestore
            .collection(NetworkChangeList.COLLECTION_NAME)
            .document(networkChangeList.id)
            .set(changeList)
            .await()
    }

    override suspend fun getChangeList(after: Int): List<NetworkChangeList> {
        val querySnapshot = firestore
            .collection(NetworkChangeList.COLLECTION_NAME)
            .whereGreaterThan(NetworkChangeList.CHANGE_LIST_VERSION, after)
            .get()
            .await()

        return querySnapshot.map { it.toObject<NetworkChangeList>() }
    }
}