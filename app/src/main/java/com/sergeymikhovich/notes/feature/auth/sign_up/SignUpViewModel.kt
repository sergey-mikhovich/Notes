package com.sergeymikhovich.notes.feature.auth.sign_up

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.sergeymikhovich.notes.core.common.extentions.isValidEmail
import com.sergeymikhovich.notes.core.common.extentions.isValidPassword
import com.sergeymikhovich.notes.core.data.repository.AccountRepository
import com.sergeymikhovich.notes.feature.auth.sign_up.navigation.SignUpRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val router: SignUpRouter
): ViewModel(), SignUpRouter by router {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.update { newEmail }
    }

    fun updatePassword(newPassword: String) {
        _password.update { newPassword }
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.update { newConfirmPassword }
    }

    fun onSignUpClick() {
        viewModelScope.launch {
//            if (!_email.value.isValidEmail()) {
//                throw IllegalArgumentException("Invalid email format")
//            }
//
//            if (!_password.value.isValidPassword()) {
//                throw IllegalArgumentException("Invalid password format")
//            }

            if (_password.value != _confirmPassword.value) {
                throw IllegalArgumentException("Passwords do not match")
            }

            accountRepository.createAccountWithEmailAndPassword(_email.value, _password.value)

            toNotes()
        }
    }

    fun onSignUpWithGoogle(credential: Credential) {
        viewModelScope.launch {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                accountRepository.signInWithGoogle(googleIdTokenCredential.idToken)
                toNotes()
            } else {
                Log.e("SignUpViewModel", "Cannot sign up with Google")
            }
        }
    }
}