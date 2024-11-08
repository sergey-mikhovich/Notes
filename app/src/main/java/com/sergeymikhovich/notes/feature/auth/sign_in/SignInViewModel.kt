package com.sergeymikhovich.notes.feature.auth.sign_in

import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.sergeymikhovich.notes.core.common.error_handling.TypingErrors
import com.sergeymikhovich.notes.core.common.error_handling.isValidEmail
import com.sergeymikhovich.notes.core.data.repository.AccountRepository
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignInScreenState(
    val signInState: SignInState
)

sealed interface SignInState {
    data class Content(
        val email: String = "",
        val password: String = "",
        val userMessage: String = "",
        val contentState: SignInContentState = SignInContentState.Idle
    ): SignInState
}

sealed interface SignInContentState {
    val isInvalidEmail: Boolean
        get() = (this as? Error)?.typingErrors?.isInvalidEmail ?: false

    val isInvalidPassword: Boolean
        get() = (this as? Error)?.typingErrors?.isInvalidPassword ?: false

    val isLoading: Boolean
        get() = this is Loading

    data object Idle: SignInContentState
    data object Loading: SignInContentState
    data class Error(
        val typingErrors: TypingErrors = TypingErrors()
    ): SignInContentState
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val router: SignInRouter,
    private val accountRepository: AccountRepository
): ViewModel(), SignInRouter by router {

    private val _state: MutableStateFlow<SignInScreenState> = MutableStateFlow(
        SignInScreenState(SignInState.Content())
    )
    val state = _state.asStateFlow()

    fun updateEmail(newEmail: String) {
        onSignInContentState {
            _state.update {
                it.copy(signInState = copy(
                    email = newEmail,
                    contentState = onContentErrorOrCreate {
                        copy(typingErrors = typingErrors.copy(isInvalidEmail = false))
                    }
                ))
            }
        }
    }

    fun updatePassword(newPassword: String) {
        onSignInContentState {
            _state.update {
                it.copy(signInState = copy(
                    password = newPassword,
                    contentState = onContentErrorOrCreate {
                        copy(typingErrors = typingErrors.copy(isInvalidPassword = false))
                    }
                ))
            }
        }
    }

    fun toastShown() {
        onSignInContentState {
            _state.update {
                it.copy(signInState = copy(userMessage = ""))
            }
        }
    }

    fun onSignInClick() {
        onSignInContentState {
            viewModelScope.launch {
                if (!email.isValidEmail()) {
                    _state.update {
                        it.copy(
                            signInState = copy(
                                contentState = onContentErrorOrCreate {
                                    copy(typingErrors = typingErrors.copy(isInvalidEmail = true))
                                }
                            )
                        )
                    }
                }

                if (password.isBlank()) {
                    _state.update {
                        it.copy(
                            signInState = copy(
                                contentState = onContentErrorOrCreate {
                                    copy(typingErrors = typingErrors.copy(isInvalidPassword = true))
                                }
                            )
                        )
                    }
                }

                if (!email.isValidEmail() || password.isBlank())
                    return@launch

                _state.update {
                    it.copy(signInState = copy(contentState = SignInContentState.Loading))
                }

                accountRepository.signInWithEmailAndPassword(email, password)
                    .onSuccess { toNotes() }
                    .onFailure { error ->
                        _state.update {
                            it.copy(signInState = copy(
                                userMessage = error.message ?: "Oops...Something went wrong"
                            ))
                        }
                    }
            }
        }
    }

    fun onSignInWithGoogle(credential: Credential) {
        onSignInContentState {
            viewModelScope.launch {
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    accountRepository.signInWithGoogle(googleIdTokenCredential.idToken)
                        .onSuccess { toNotes() }
                        .onFailure { error ->
                            _state.update {
                                it.copy(signInState = copy(
                                    userMessage = error.message ?: "Oops...Something went wrong"
                                ))
                            }
                        }
                } else {
                    _state.update {
                        it.copy(signInState = copy(userMessage = "Oops...Something went wrong"))
                    }
                }
            }
        }
    }

    private inline fun onSignInContentState(block: SignInState.Content.() -> Unit) =
        (_state.value.signInState as? SignInState.Content)?.let(block)

    private fun onContentErrorOrCreate(
        block: SignInContentState.Error.() -> SignInContentState.Error
    ) =
        (_state.value.signInState as? SignInState.Content)?.let { content ->
            (content.contentState as? SignInContentState.Error)
        }.let {
            block(it ?: SignInContentState.Error(TypingErrors()))
        }
}