package com.sergeymikhovich.notes.core.common.di

import com.sergeymikhovich.notes.core.common.navigation.ComposeNavigator
import com.sergeymikhovich.notes.core.common.navigation.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigatorModule {

    @Binds
    @Singleton
    fun bindNavigator(navigator: ComposeNavigator): Navigator
}