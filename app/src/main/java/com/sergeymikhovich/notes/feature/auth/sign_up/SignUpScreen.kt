package com.sergeymikhovich.notes.feature.auth.sign_up

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.R
import com.sergeymikhovich.notes.core.common.error_handling.arePasswordsNotMatch
import com.sergeymikhovich.notes.core.common.error_handling.isInvalidEmail
import com.sergeymikhovich.notes.core.common.error_handling.isInvalidPassword
import com.sergeymikhovich.notes.core.common.navigation.composableTo
import com.sergeymikhovich.notes.core.design_system.component.AuthButton
import com.sergeymikhovich.notes.core.design_system.component.AuthDescriptionText
import com.sergeymikhovich.notes.core.design_system.component.AuthOtherwiseButton
import com.sergeymikhovich.notes.core.design_system.component.AuthOutLinedTextField
import com.sergeymikhovich.notes.core.design_system.component.AuthOutlinedButtonText
import com.sergeymikhovich.notes.core.design_system.component.AuthTitleText
import com.sergeymikhovich.notes.feature.auth.sign_up.navigation.SignUpDirection

fun NavGraphBuilder.composableToSignUp() = composableTo(SignUpDirection) { SignUpScreen() }

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state) {
        if (state.error.isNotBlank()){
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            viewModel.toastShown()
        }
    }

    SignUpContent(
        state = state,
        updateEmail = viewModel::updateEmail,
        updatePassword = viewModel::updatePassword,
        updateConfirmPassword = viewModel::updateConfirmPassword,
        onSignUpClick = viewModel::onSignUpClick,
        onSignInClick = viewModel::toSignIn
    )
}

@Composable
private fun SignUpContent(
    state: SignUpUiState,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    updateConfirmPassword: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth().background(Color(0xFFFFFDF0)),
            color = if (state.isLoading) Color(0xFFD9614C) else Color(0xFFFFFDF0)
        )
        Column(
            modifier = Modifier
                .background(color = Color(0xFFFFFDF0))
                .padding(
                    start = 16.dp,
                    top = 32.dp,
                    end = 16.dp
                )
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AuthTitleText(text = "Register")
                Spacer(modifier = Modifier.height(16.dp))
                AuthDescriptionText(text = "And start taking notes")
            }
            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    AuthOutlinedButtonText(text = stringResource(R.string.email))
                    Spacer(modifier = Modifier.height(12.dp))

                    AuthOutLinedTextField(
                        value = state.data.email,
                        onValueChange = updateEmail,
                        placeholderText = "sergey.mikhovich@gmail.com",
                        isError = state.data.typingErrors.isInvalidEmail(),
                        supportingText = if(state.data.email.isBlank()) "Required field" else "Invalid email"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    AuthOutlinedButtonText(text = stringResource(R.string.password))
                    Spacer(modifier = Modifier.height(12.dp))
                    AuthOutLinedTextField(
                        value = state.data.password,
                        onValueChange = updatePassword,
                        placeholderText = "∗∗∗∗∗∗∗∗",
                        isError = state.data.typingErrors.isInvalidPassword(),
                        supportingText = if (state.data.password.isBlank()) "Required field" else "Invalid password"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    AuthOutlinedButtonText(text = stringResource(R.string.confirm_password))
                    Spacer(modifier = Modifier.height(12.dp))

                    AuthOutLinedTextField(
                        value = state.data.confirmPassword,
                        onValueChange = updateConfirmPassword,
                        placeholderText = "∗∗∗∗∗∗∗∗",
                        isError = state.data.typingErrors.arePasswordsNotMatch(),
                        supportingText = if (state.data.confirmPassword.isBlank()) "Required field" else "Passwords don't match"
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                AuthButton(onClick = onSignUpClick, text = stringResource(R.string.sign_up))
                Spacer(modifier = Modifier.height(16.dp))

                AuthOtherwiseButton(
                    text = "Already have an account? Login here",
                    onClick = onSignInClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    SignUpContent(
        state = SignUpUiState(),
        updateEmail = {},
        updatePassword = {},
        updateConfirmPassword = {},
        onSignUpClick = {},
        onSignInClick = {}
    )
}