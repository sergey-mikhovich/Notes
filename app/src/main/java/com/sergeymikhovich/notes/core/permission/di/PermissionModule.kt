package com.sergeymikhovich.notes.core.permission.di

import com.sergeymikhovich.notes.core.permission.PermissionManager
import com.sergeymikhovich.notes.core.permission.PermissionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PermissionModule {

    @Binds
    fun bindsPermissionManager(permissionManager: PermissionManagerImpl): PermissionManager
}