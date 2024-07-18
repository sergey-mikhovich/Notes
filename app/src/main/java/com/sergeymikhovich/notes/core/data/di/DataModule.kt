package com.sergeymikhovich.notes.core.data.di

import com.sergeymikhovich.notes.core.data.mapper.NoteMapper
import com.sergeymikhovich.notes.core.data.repository.NoteRepository
import com.sergeymikhovich.notes.core.data.repository.OfflineFirstNoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NoteRepositoryModule {

    @Binds
    fun bindsRepository(repository: OfflineFirstNoteRepository): NoteRepository

    companion object {

        @Provides
        fun provideNoteMapper(): NoteMapper = NoteMapper()
    }
}