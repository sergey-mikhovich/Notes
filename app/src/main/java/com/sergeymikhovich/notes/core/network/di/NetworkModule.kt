package com.sergeymikhovich.notes.core.network.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sergeymikhovich.notes.core.network.CloudFirestoreNoteDataSourceImpl
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
    fun bindsRemoteNoteDataSource(firestore: CloudFirestoreNoteDataSourceImpl): NetworkNoteDataSource

    companion object {
        @Provides
        @Singleton
        fun providesFirebaseFirestore(): FirebaseFirestore = Firebase.firestore
    }
}