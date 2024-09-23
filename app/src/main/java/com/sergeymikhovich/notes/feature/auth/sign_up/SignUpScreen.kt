package com.sergeymikhovich.notes.feature.auth.sign_up

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.R
import com.sergeymikhovich.notes.core.common.navigation.composableTo
import com.sergeymikhovich.notes.core.design_system.component.AuthButton
import com.sergeymikhovich.notes.core.design_system.component.AuthDescriptionText
import com.sergeymikhovich.notes.core.design_system.component.AuthOtherwiseButton
import com.sergeymikhovich.notes.core.design_system.component.AuthOutLinedTextField
import com.sergeymikhovich.notes.core.design_system.component.AuthOutlinedButtonText
import com.sergeymikhovich.notes.core.design_system.component.AuthTitleText
import com.sergeymikhovich.notes.core.design_system.component.WithTextCenteredDivider
import com.sergeymikhovich.notes.feature.auth.AuthenticationButton
import com.sergeymikhovich.notes.feature.auth.sign_up.navigation.SignUpDirection

fun NavGraphBuilder.composableToSignUp() = composableTo(SignUpDirection) { SignUpScreen() }

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val confirmPassword by viewModel.confirmPassword.collectAsStateWithLifecycle()

    SignUpContent(
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        updateEmail = viewModel::updateEmail,
        updatePassword = viewModel::updatePassword,
        updateConfirmPassword = viewModel::updateConfirmPassword,
        onSignUpClick = viewModel::onSignUpClick,
        onSignUpWithGoogleClick = viewModel::onSignUpWithGoogle,
        onSignInClick = viewModel::toSignIn
    )
}

@Composable
private fun SignUpContent(
    email: String,
    password: String,
    confirmPassword: String,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    updateConfirmPassword: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignUpWithGoogleClick: (Credential) -> Unit,
    onSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = Color(0xFFF8EEE2))
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
                    value = email,
                    onValueChange = updateEmail,
                    placeholderText = "sergey.mikhovich@gmail.com"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                AuthOutlinedButtonText(text = stringResource(R.string.password))
                Spacer(modifier = Modifier.height(12.dp))

                AuthOutLinedTextField(
                    value = password,
                    onValueChange = updatePassword,
                    placeholderText = "********"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                AuthOutlinedButtonText(text = stringResource(R.string.confirm_password))
                Spacer(modifier = Modifier.height(12.dp))

                AuthOutLinedTextField(
                    value = confirmPassword,
                    onValueChange = updateConfirmPassword,
                    placeholderText = "********"
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            AuthButton(onClick = onSignUpClick, text = stringResource(R.string.sign_up))
            Spacer(modifier = Modifier.height(16.dp))
            WithTextCenteredDivider(centeredText = stringResource(R.string.or))
            Spacer(modifier = Modifier.height(16.dp))

            AuthenticationButton(
                buttonText = R.string.sign_up_with_google,
                onGetCredentialResponse = onSignUpWithGoogleClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuthOtherwiseButton(
                text = "Already have an account? Login here",
                onClick = onSignInClick
            )
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    SignUpContent(
        email = "",
        password = "",
        confirmPassword = "",
        updateEmail = {},
        updatePassword = {},
        updateConfirmPassword = {},
        onSignUpClick = {},
        onSignUpWithGoogleClick = {},
        onSignInClick = {}
    )
}