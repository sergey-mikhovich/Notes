package com.sergeymikhovich.notes.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

const val DATA_STORE_FILE_NAME = "user_preferences.pb"

//class NotesPreferencesDataStore @Inject constructor(
//    private val userPreferencesDataStore: DataStore<UserPreferences>
//) {
//
//    suspend fun getSyncTimes() = userPreferencesDataStore.data
//        .map {
//            SyncTimes(
//                lastNotesSyncTime = it.lastNotesSyncTime
//            )
//        }.firstOrNull() ?: SyncTimes()
//
//    suspend fun updateSyncTimes(update: SyncTimes.() -> SyncTimes) {
//        try {
//            userPreferencesDataStore.updateData { currentPreferences ->
//                val updatedChangeListVersions = update(
//                    SyncTimes(
//                        lastNotesSyncTime = currentPreferences.lastNotesSyncTime
//                    )
//                )
//
//                currentPreferences.copy {
//                    lastNotesSyncTime = updatedChangeListVersions.lastNotesSyncTime
//                }
//            }
//        } catch (ioException: IOException) {
//            Log.e("NotesPreferences", "Failed to update user preferences", ioException)
//        }
//    }
//}