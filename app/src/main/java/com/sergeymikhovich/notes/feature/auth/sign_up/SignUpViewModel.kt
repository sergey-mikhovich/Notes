package com.sergeymikhovich.notes.feature.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.sergeymikhovich.notes.core.common.error_handling.TypingError
import com.sergeymikhovich.notes.core.common.error_handling.TypingError.InvalidEmail
import com.sergeymikhovich.notes.core.common.error_handling.TypingError.InvalidPassword
import com.sergeymikhovich.notes.core.common.error_handling.TypingError.PasswordsNotMatch
import com.sergeymikhovich.notes.core.common.error_handling.isPasswordConfirmed
import com.sergeymikhovich.notes.core.common.error_handling.isValidEmail
import com.sergeymikhovich.notes.core.common.error_handling.isValidPassword
import com.sergeymikhovich.notes.core.data.repository.AccountRepository
import com.sergeymikhovich.notes.feature.auth.sign_up.navigation.SignUpRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Data(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val typingErrors: Set<TypingError> = setOf(),
)

data class SignUpUiState(
    val data: Data = Data(),
    val error: String = "",
    val isLoading: Boolean = false
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val router: SignUpRouter
): ViewModel(), SignUpRouter by router {

    private val _state = MutableStateFlow(SignUpUiState())
    val state = _state.asStateFlow()

    private val data: Data
        get() = _state.value.data

    fun updateEmail(newEmail: String) {
        _state.update {
            SignUpUiState(
                data = data.copy(
                    email = newEmail,
                    typingErrors = getTypingErrorsWithout(InvalidEmail)
                )
            )
        }
    }

    fun updatePassword(newPassword: String) {
        _state.update {
            SignUpUiState(
                data = data.copy(
                    password = newPassword,
                    typingErrors = getTypingErrorsWithout(InvalidPassword)
                )
            )
        }
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _state.update {
            SignUpUiState(
                data = data.copy(
                    confirmPassword = newConfirmPassword,
                    typingErrors = getTypingErrorsWithout(PasswordsNotMatch)
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
        _state.update { SignUpUiState(data = data) }
    }

    private fun showLoading() {
        _state.update { it.copy(isLoading = true) }
    }

    fun onSignUpClick() {
        viewModelScope.launch {
            val email = data.email
            val password = data.password
            val confirmPassword = data.confirmPassword

            if (!email.isValidEmail()) {
                _state.update {
                    SignUpUiState(data = data.copy(typingErrors = getTypingErrorsWith(InvalidEmail)))
                }
            }

            if (!password.isValidPassword()) {
                _state.update {
                    SignUpUiState(data = data.copy(typingErrors = getTypingErrorsWith(InvalidPassword)))
                }
            }

            if (!confirmPassword.isPasswordConfirmed(password)) {
                _state.update {
                    SignUpUiState(data = data.copy(typingErrors = getTypingErrorsWith(PasswordsNotMatch)))
                }
            }

            if (!email.isValidEmail() || !password.isValidPassword() || !confirmPassword.isPasswordConfirmed(password))
                return@launch

            showLoading()

            accountRepository.createAccountWithEmailAndPassword(data.email, data.password)
                .onSuccess { toNotes() }
                .onFailure { error ->
                    _state.update {
                        SignUpUiState(
                            data = data,
                            error = error.message ?: "Ooops...Something went wrong"
                        )
                    }
                }
        }
    }
}