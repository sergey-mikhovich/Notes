package com.sergeymikhovich.notes.feature.auth.sign_in

import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.sergeymikhovich.notes.core.common.error_handling.TypingError
import com.sergeymikhovich.notes.core.common.error_handling.TypingError.InvalidEmail
import com.sergeymikhovich.notes.core.common.error_handling.TypingError.InvalidPassword
import com.sergeymikhovich.notes.core.common.error_handling.isValidEmail
import com.sergeymikhovich.notes.core.data.repository.AccountRepository
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Data(
    val email: String = "",
    val password: String = "",
    val typingErrors: Set<TypingError> = setOf(),
)

data class SignInUiState(
    val data: Data = Data(),
    val error: String = "",
    val isLoading: Boolean = false
)

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val router: SignInRouter,
    private val accountRepository: AccountRepository
): ViewModel(), SignInRouter by router {

    private val _state = MutableStateFlow(SignInUiState())
    val state = _state.asStateFlow()

    private val data: Data
        get() = _state.value.data

    fun updateEmail(newEmail: String) {
        _state.update {
            SignInUiState(
                data = data.copy(
                    email = newEmail,
                    typingErrors = getTypingErrorsWithout(InvalidEmail)
                )
            )
        }
    }

    fun updatePassword(newPassword: String) {
        _state.update {
            SignInUiState(
                data = data.copy(
                    password = newPassword,
                    typingErrors = getTypingErrorsWithout(InvalidPassword)
                )
            )
        }
    }

    private fun getTypingErrorsWithout(typingError: TypingError): Set<TypingError> {
        return data.typingErrors.minusElement(typingError)
    }

    private fun getTypingErrorsWith(typingError: TypingError): Set<TypingError> {
        return data.typingErrors.plusElement(typingError)
    }

    fun toastShown() {
        _state.update { SignInUiState(data = data) }
    }

    private fun showLoading() {
        _state.update { it.copy(isLoading = true) }
    }

    private fun showError(errorMessage: String? = null, defaultValue: String = "") {
        _state.update {
            SignInUiState(data = data, error = errorMessage ?: defaultValue)
        }
    }

    fun onSignInClick() {
        viewModelScope.launch {
            val email = data.email
            val password = data.password

            if (!email.isValidEmail()) {
                _state.update {
                    SignInUiState(data = data.copy(typingErrors = getTypingErrorsWith(InvalidEmail))
                    )
                }
            }

            if (password.isBlank()) {
                _state.update {
                    SignInUiState(data = data.copy(typingErrors = getTypingErrorsWith(InvalidPassword))
                    )
                }
            }

            if (!email.isValidEmail() || password.isBlank())
                return@launch

            showLoading()

            accountRepository.signInWithEmailAndPassword(data.email, data.password)
                .onSuccess { toNotes() }
                .onFailure { showError(it.message, "Ooops...Something went wrong") }
        }
    }

    fun onSignInWithGoogle(credential: Credential) {
        viewModelScope.launch {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                accountRepository.signInWithGoogle(googleIdTokenCredential.idToken)
                    .onSuccess { toNotes() }
                    .onFailure { showError(it.message, "Ooops...Something went wrong") }
            } else {
                showError(defaultValue = "Ooops...Something went wrong")
            }
        }
    }
}