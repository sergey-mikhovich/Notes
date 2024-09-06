package com.sergeymikhovich.notes.feature.auth.sign_in

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.sergeymikhovich.notes.core.data.repository.AccountRepository
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val router: SignInRouter,
    private val accountRepository: AccountRepository
): ViewModel(), SignInRouter by router {

    // Backing properties to avoid state updates from other classes
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun onSignInClick() {
        viewModelScope.launch {
            accountRepository.signInWithEmail(_email.value, _password.value)
            toNotes()
        }
    }

    fun onSignInWithGoogle(credential: Credential) {
        viewModelScope.launch {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                accountRepository.signInWithGoogle(googleIdTokenCredential.idToken)
                toNotes()
            } else {
                Log.e("SignInViewModel", "Cannot sign in with Google")
            }
        }
    }
}