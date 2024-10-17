package com.sergeymikhovich.notes.core.data.repository

import com.sergeymikhovich.notes.core.common.error_handling.UnitResult
import com.sergeymikhovich.notes.core.model.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun observeCurrentUser(): Flow<User>
    fun hasUser(): Boolean
    fun getUserProfile(): User
    suspend fun createAnonymousAccount(): UnitResult
    suspend fun createAccountWithEmailAndPassword(email: String, password: String): UnitResult
    suspend fun signInWithGoogle(idToken: String): UnitResult
    suspend fun signInWithEmailAndPassword(email: String, password: String): UnitResult
    suspend fun signOut(): UnitResult
    suspend fun deleteAccount(): UnitResult
}