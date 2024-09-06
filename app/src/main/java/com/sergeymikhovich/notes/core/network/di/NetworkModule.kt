package com.sergeymikhovich.notes.core.network.di

import com.sergeymikhovich.notes.core.network.CloudFirestoreChangeNoteDataSource
import com.sergeymikhovich.notes.core.network.CloudFirestoreNoteDataSource
import com.sergeymikhovich.notes.core.network.NetworkChangeNoteDataSource
import com.sergeymikhovich.notes.core.network.NetworkNoteDataSource
import dagger.Binds
import dagger.Module
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
}