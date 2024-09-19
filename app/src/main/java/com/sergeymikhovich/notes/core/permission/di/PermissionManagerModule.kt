package com.sergeymikhovich.notes.core.permission.di

import com.sergeymikhovich.notes.core.permission.PermissionManager
import com.sergeymikhovich.notes.core.permission.PermissionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PermissionManagerModule {

    @Binds
    @Singleton
    fun bindPermissionManager(permissionManager: PermissionManagerImpl): PermissionManager
}