package com.sergeymikhovich.notes.core.common.di

import com.sergeymikhovich.notes.core.common.di.NoteDispatchers.Default
import com.sergeymikhovich.notes.core.common.di.NoteDispatchers.IO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

enum class NoteDispatchers {
    Default,
    IO
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class Dispatcher(val noteDispatcher: NoteDispatchers)

@Module
@InstallIn(SingletonComponent::class)
object CoroutineContextModule {

    @Provides
    @Dispatcher(IO)
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(Default)
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default
}