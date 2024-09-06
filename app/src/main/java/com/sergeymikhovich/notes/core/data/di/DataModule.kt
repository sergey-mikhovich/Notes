package com.sergeymikhovich.notes.core.data.di

import com.sergeymikhovich.notes.core.data.mapper.AccountMapper
import com.sergeymikhovich.notes.core.data.mapper.NoteMapper
import com.sergeymikhovich.notes.core.data.repository.AccountRepository
import com.sergeymikhovich.notes.core.data.repository.FirebaseAccountRepository
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
    fun bindsNoteRepository(repository: OfflineFirstNoteRepository): NoteRepository

    @Binds
    fun bindsAccountRepository(repository: FirebaseAccountRepository): AccountRepository

    companion object {

        @Provides
        fun providesNoteMapper(): NoteMapper = NoteMapper()

        @Provides
        fun providesAccountMapper(): AccountMapper = AccountMapper()
    }
}