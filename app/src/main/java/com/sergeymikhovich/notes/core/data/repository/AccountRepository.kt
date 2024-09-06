package com.sergeymikhovich.notes.core.data.repository

import com.sergeymikhovich.notes.core.model.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun observeCurrentUser(): Flow<User?>
    fun hasUser(): Boolean
    fun getUserProfile(): User
    suspend fun createAnonymousAccount()
    suspend fun linkAccountWithGoogle(idToken: String)
    suspend fun linkAccountWithEmail(email: String, password: String)
    suspend fun signInWithGoogle(idToken: String)
    suspend fun signInWithEmail(email: String, password: String)
    suspend fun signOut()
    suspend fun deleteAccount()
}