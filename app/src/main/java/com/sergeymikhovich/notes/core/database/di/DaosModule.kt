package com.sergeymikhovich.notes.core.database.di

import com.sergeymikhovich.notes.core.database.AppDatabase
import com.sergeymikhovich.notes.core.database.dao.ChangeNoteDao
import com.sergeymikhovich.notes.core.database.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaosModule {

    @Provides
    fun providesNoteDao(
        database: AppDatabase
    ): NoteDao = database.noteDao()

    @Provides
    fun providesChangeNoteDao(
        database: AppDatabase
    ): ChangeNoteDao = database.changeNote()
}