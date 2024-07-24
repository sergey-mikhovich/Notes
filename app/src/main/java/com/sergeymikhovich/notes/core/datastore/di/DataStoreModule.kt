package com.sergeymikhovich.notes.core.datastore.di

//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.core.DataStoreFactory
//import androidx.datastore.dataStoreFile
//import com.sergeymikhovich.notes.core.common.di.ApplicationScope
//import com.sergeymikhovich.notes.core.common.di.Dispatcher
//import com.sergeymikhovich.notes.core.common.di.NoteDispatchers.IO
//import com.sergeymikhovich.notes.core.datastore.DATA_STORE_FILE_NAME
//import com.sergeymikhovich.notes.core.datastore.UserPreferences
//import com.sergeymikhovich.notes.core.datastore.UserPreferencesSerializer
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.CoroutineScope
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DataStoreModule {
//
//    @Provides
//    @Singleton
//    fun providesUserPreferencesDataStore(
//        @ApplicationContext context: Context,
//        @Dispatcher(IO) ioDispatcherIO: CoroutineDispatcher,
//        @ApplicationScope scope: CoroutineScope,
//        userPreferencesSerializer: UserPreferencesSerializer
//    ): DataStore<UserPreferences> =
//        DataStoreFactory.create(
//            serializer = userPreferencesSerializer,
//            scope = CoroutineScope(scope.coroutineContext + ioDispatcherIO)
//        ) {
//            context.dataStoreFile(DATA_STORE_FILE_NAME)
//        }
//}