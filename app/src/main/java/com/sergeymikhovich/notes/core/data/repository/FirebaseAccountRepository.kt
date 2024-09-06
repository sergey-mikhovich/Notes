package com.sergeymikhovich.notes.core.data.repository

import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sergeymikhovich.notes.core.common.di.ApplicationScope
import com.sergeymikhovich.notes.core.common.di.Dispatcher
import com.sergeymikhovich.notes.core.common.di.NoteDispatchers
import com.sergeymikhovich.notes.core.data.mapper.AccountMapper
import com.sergeymikhovich.notes.core.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAccountRepository @Inject constructor(
    private val accountMapper: AccountMapper,
    @Dispatcher(NoteDispatchers.IO) private val context: CoroutineDispatcher,
    @ApplicationScope scope: CoroutineScope
): AccountRepository {

    override fun observeCurrentUser(): Flow<User?> =
        callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                trySend(accountMapper.firebaseToDomainUser(auth.currentUser))
            }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }.distinctUntilChanged()

    override fun hasUser(): Boolean =
        Firebase.auth.currentUser != null

    override fun getUserProfile(): User =
        accountMapper.firebaseToDomainUser(Firebase.auth.currentUser)

    override suspend fun createAnonymousAccount() {
        withContext(context) {
            Firebase.auth.signInAnonymously().await()
        }
    }

    override suspend fun linkAccountWithGoogle(idToken: String) {
        withContext(context) {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            Firebase.auth.currentUser!!.linkWithCredential(firebaseCredential).await()
        }
    }

    override suspend fun linkAccountWithEmail(email: String, password: String) {
        withContext(context) {
            val credential = EmailAuthProvider.getCredential(email, password)
            Firebase.auth.currentUser!!.linkWithCredential(credential).await()
        }
    }

    override suspend fun signInWithGoogle(idToken: String) {
        withContext(context) {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            Firebase.auth.signInWithCredential(firebaseCredential).await()
        }
    }

    override suspend fun signInWithEmail(email: String, password: String) {
        withContext(context) {
            Firebase.auth.signInWithEmailAndPassword(email, password)
        }
    }

    override suspend fun signOut() = withContext(context) {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        withContext(context) {
            Firebase.auth.currentUser!!.delete().await()
        }
    }
}