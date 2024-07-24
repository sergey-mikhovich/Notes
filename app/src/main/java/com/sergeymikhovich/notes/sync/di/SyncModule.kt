package com.sergeymikhovich.notes.sync.di

import com.sergeymikhovich.notes.core.data.SyncManager
import com.sergeymikhovich.notes.sync.status.WorkManagerSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SyncModule {

    @Binds
    fun bindsSyncManager(
        syncManager: WorkManagerSyncManager
    ): SyncManager
}