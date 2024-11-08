package com.sergeymikhovich.notes.feature.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.sergeymikhovich.notes.core.common.error_handling.TypingErrors
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

data class SignUpScreenState(
    val signUpState: SignUpState
)

sealed interface SignUpState {
    data object Loading: SignUpState
    data object Error: SignUpState
    data class Content(
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val userMessage: String = "",
        val contentState: SignUpContentState = SignUpContentState.Idle
    ): SignUpState
}

sealed interface SignUpContentState {
    val isInvalidEmail: Boolean
        get() = (this as? Error)?.typingErrors?.isInvalidEmail ?: false

    val isInvalidPassword: Boolean
        get() = (this as? Error)?.typingErrors?.isInvalidPassword ?: false

    val arePasswordsNotMatch: Boolean
        get() = (this as? Error)?.typingErrors?.arePasswordsNotMatch ?: false

    val isLoading: Boolean
        get() = this is Loading

    data object Idle: SignUpContentState
    data object Loading: SignUpContentState
    data class Error(
        val typingErrors: TypingErrors = TypingErrors()
    ): SignUpContentState
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val router: SignUpRouter
): ViewModel(), SignUpRouter by router {

    private val _state: MutableStateFlow<SignUpScreenState> = MutableStateFlow(
        SignUpScreenState(SignUpState.Content())
    )
    val state = _state.asStateFlow()

    fun updateEmail(newEmail: String) {
        onSignUpContentState {
            _state.update {
                it.copy(signUpState = copy(
                    email = newEmail,
                    contentState = onContentErrorOrCreate {
                        copy(typingErrors = typingErrors.copy(isInvalidEmail = false))
                    },
                ))
            }
        }
    }

    fun updatePassword(newPassword: String) {
        onSignUpContentState {
            _state.update {
                it.copy(signUpState = copy(
                    password = newPassword,
                    contentState = onContentErrorOrCreate {
                        copy(typingErrors = typingErrors.copy(isInvalidPassword = false))
                    }
                ))
            }
        }
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        onSignUpContentState {
            _state.update {
                it.copy(signUpState = copy(
                    confirmPassword = newConfirmPassword,
                    contentState = onContentErrorOrCreate {
                        copy(typingErrors = typingErrors.copy(arePasswordsNotMatch = false))
                    }
                ))
            }
        }
    }

    fun toastShown() {
        onSignUpContentState {
            _state.update {
                it.copy(signUpState = copy(userMessage = ""))
            }
        }
    }

    fun onSignUpClick() {
        onSignUpContentState {
            viewModelScope.launch {
                if (!email.isValidEmail()) {
                    _state.update {
                        it.copy(signUpState = copy(
                            contentState = onContentErrorOrCreate {
                                copy(typingErrors = typingErrors.copy(isInvalidEmail = true))
                            },
                        ))
                    }
                }

                if (!password.isValidPassword()) {
                    _state.update {
                        it.copy(signUpState = copy(
                            contentState = onContentErrorOrCreate {
                                copy(typingErrors = typingErrors.copy(isInvalidPassword = true))
                            },
                        ))
                    }
                }

                if (!confirmPassword.isPasswordConfirmed(password)) {
                    _state.update {
                        it.copy(signUpState = copy(
                            contentState = onContentErrorOrCreate {
                                copy(typingErrors = typingErrors.copy(arePasswordsNotMatch = true))
                            },
                        ))
                    }
                }

                if (!email.isValidEmail() ||
                    !password.isValidPassword() ||
                    !confirmPassword.isPasswordConfirmed(password)) return@launch

                _state.update {
                    it.copy(signUpState = SignUpState.Loading)
                }

                accountRepository.createAccountWithEmailAndPassword(email, password)
                    .onSuccess { toNotes() }
                    .onFailure { error ->
                        _state.update {
                            it.copy(signUpState = copy(
                                userMessage = error.message ?: "Oops...Something went wrong")
                            )
                        }
                    }
            }
        }
    }

    private inline fun onSignUpContentState(block: SignUpState.Content.() -> Unit) =
        (_state.value.signUpState as? SignUpState.Content)?.let(block)

    private fun onContentErrorOrCreate(
        block: SignUpContentState.Error.() -> SignUpContentState.Error
    ) =
        (_state.value.signUpState as? SignUpState.Content)?.let { content ->
            (content.contentState as? SignUpContentState.Error)
        }.let {
            block(it ?: SignUpContentState.Error(TypingErrors()))
        }
}