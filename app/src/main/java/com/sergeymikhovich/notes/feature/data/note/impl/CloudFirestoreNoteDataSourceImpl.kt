package com.sergeymikhovich.notes.feature.data.note.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.sergeymikhovich.notes.feature.data.note.api.RemoteNoteDataSource
import com.sergeymikhovich.notes.feature.data.note.impl.remote.NoteFirebase
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
) : RemoteNoteDataSource {

    override fun observeAll(): Flow<List<NoteFirebase>> {
        return callbackFlow {
            firestore
                .collection(NoteFirebase.COLLECTION_NAME)
                .addSnapshotListener { snapshot, exception ->
                    if (snapshot != null && !snapshot.isEmpty) {
                        trySend(snapshot.map { it.toObject<NoteFirebase>() })
                    }
                }
            awaitClose {  }
        }
    }

    override suspend fun loadAll(): List<NoteFirebase> {
        val querySnapshot = firestore
            .collection(NoteFirebase.COLLECTION_NAME)
            .get()
            .await()

        return querySnapshot.map { it.toObject<NoteFirebase>() }
    }

    override suspend fun deleteById(id: String) {
        firestore
            .collection(NoteFirebase.COLLECTION_NAME)
            .document(id)
            .delete()
            .await()
    }

    override suspend fun upsert(noteFirebase: NoteFirebase) {
        val note = hashMapOf(
            NoteFirebase.ID to noteFirebase.id,
            NoteFirebase.TITLE to noteFirebase.title,
            NoteFirebase.DESCRIPTION to noteFirebase.description
        )

        firestore
            .collection(NoteFirebase.COLLECTION_NAME)
            .document(noteFirebase.id)
            .set(note)
            .await()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface CloudFirestoreDataSourceModule {

    @Binds
    @Singleton
    fun bindsRemoteNoteDataSource(firestore: CloudFirestoreNoteDataSourceImpl): RemoteNoteDataSource

    companion object {
        @Provides
        @Singleton
        fun providesFirebaseFirestore(): FirebaseFirestore = Firebase.firestore
    }
}