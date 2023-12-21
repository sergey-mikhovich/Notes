package com.sergeymikhovich.notes.common.di.context

import com.sergeymikhovich.notes.common.di.scope.ApplicationScope
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class DispatchersImpl @Inject constructor(
    override val main: CoroutineContext = kotlinx.coroutines.Dispatchers.Main,
    override val default: CoroutineContext = kotlinx.coroutines.Dispatchers.Default,
    override val unconfined: CoroutineContext = kotlinx.coroutines.Dispatchers.Unconfined,
    override val io: CoroutineContext = kotlinx.coroutines.Dispatchers.IO,
    override val log: CoroutineContext = kotlinx.coroutines.Dispatchers.IO.limitedParallelism(1)
) : Dispatchers

@Module
interface DispatchersModule {

    @Binds
    @ApplicationScope
    fun bindDispatchers(dispatchers: DispatchersImpl): Dispatchers
}