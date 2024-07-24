package com.sergeymikhovich.notes.core.network.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sergeymikhovich.notes.core.network.CloudFirestoreChangeNoteDataSource
import com.sergeymikhovich.notes.core.network.CloudFirestoreNoteDataSource
import com.sergeymikhovich.notes.core.network.NetworkChangeNoteDataSource
import com.sergeymikhovich.notes.core.network.NetworkNoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    @Singleton
    fun bindsNetworkNoteDataSource(
        datasource: CloudFirestoreNoteDataSource
    ): NetworkNoteDataSource

    @Binds
    @Singleton
    fun bindsNetworkChangeNoteDataSource(
        datasource: CloudFirestoreChangeNoteDataSource
    ): NetworkChangeNoteDataSource

    companion object {
        @Provides
        @Singleton
        fun providesFirebaseFirestore(): FirebaseFirestore =Firebase.firestore
//            Firebase.firestore.apply {
//                useEmulator("10.0.2.2", 8080)
//                firestoreSettings = firestoreSettings {
//                    isPersistenceEnabled = false
//                }
//            }
    }
}