package com.sergeymikhovich.notes.core.notification.di

import com.sergeymikhovich.notes.core.notification.Notifier
import com.sergeymikhovich.notes.core.notification.SystemPushNotifier
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NotifierModule {

    @Binds
    fun bindsNotifier(notifier: SystemPushNotifier): Notifier
}