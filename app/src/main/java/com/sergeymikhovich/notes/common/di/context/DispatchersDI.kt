package com.sergeymikhovich.notes.common.di.context

import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

@Qualifier
annotation class DispatcherMain

@Qualifier
annotation class DispatcherIO

@Qualifier
annotation class DispatcherUnconfined

@Qualifier
annotation class DispatcherDefault

@Qualifier
annotation class DispatcherLog

@Module
class CoroutineContextModule {

    @Provides
    @DispatcherMain
    fun provideDispatcherMain(dispatchers: Dispatchers): CoroutineContext = dispatchers.main

    @Provides
    @DispatcherIO
    fun provideDispatcherIO(dispatchers: Dispatchers): CoroutineContext = dispatchers.io

    @Provides
    @DispatcherUnconfined
    fun provideDispatcherUnconfined(dispatchers: Dispatchers): CoroutineContext = dispatchers.unconfined

    @Provides
    @DispatcherDefault
    fun provideDispatcherDefault(dispatchers: Dispatchers): CoroutineContext = dispatchers.default

    @Provides
    @DispatcherLog
    fun provideDispatcherLog(dispatchers: Dispatchers): CoroutineContext = dispatchers.log
}