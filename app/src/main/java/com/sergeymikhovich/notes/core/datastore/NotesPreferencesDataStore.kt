package com.sergeymikhovich.notes.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

const val DATA_STORE_FILE_NAME = "user_preferences.pb"

class NotesPreferencesDataStore @Inject constructor(
    private val userPreferencesDataStore: DataStore<UserPreferences>
) {

    suspend fun getChangeListVersions() = userPreferencesDataStore.data
        .map {
            ChangeListVersions(
                noteListVersion = it.changeListVersion
            )
        }.firstOrNull() ?: ChangeListVersions()

    suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions) {
        try {
            userPreferencesDataStore.updateData { currentPreferences ->
                val updatedChangeListVersions = update(
                    ChangeListVersions(
                        noteListVersion = currentPreferences.changeListVersion
                    )
                )

                currentPreferences.copy {
                    changeListVersion = updatedChangeListVersions.noteListVersion
                }
            }
        } catch (ioException: IOException) {
            Log.e("NotesPreferences", "Failed to update user preferences", ioException)
        }
    }
}