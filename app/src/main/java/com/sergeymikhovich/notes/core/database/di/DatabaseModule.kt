package com.sergeymikhovich.notes.core.database.di

import android.content.Context
import androidx.room.Room
import com.sergeymikhovich.notes.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DB_NAME = "AppDatabase"

@Module
@InstallIn(SingletonComponent::class)
internal object AppDatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        DB_NAME
    ).build()
}