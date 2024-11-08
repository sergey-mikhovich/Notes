package com.sergeymikhovich.notes.feature.auth.sign_in

import android.util.Log
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
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInDirection

fun NavGraphBuilder.composableToSignIn() = composableTo(SignInDirection) { SignInScreen() }

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val signInState = state.signInState

    if (signInState is SignInState.Content) {
        SignInContent(
            content = signInState,
            updateEmail = viewModel::updateEmail,
            updatePassword = viewModel::updatePassword,
            onSignInClick = viewModel::onSignInClick,
            onSignInWithGoogle = viewModel::onSignInWithGoogle,
            onSignUpClick = viewModel::toSignUp,
            onToastShown = viewModel::toastShown
        )
    }
}

@Composable
private fun SignInContent(
    content: SignInState.Content,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignInWithGoogle: (Credential) -> Unit,
    onSignUpClick: () -> Unit,
    onToastShown: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth().background(Color(0xFFFFFDF0)),
            color = if (content.contentState.isLoading)
                Color(0xFFD9614C) else Color(0xFFFFFDF0)
        )

        LaunchedEffect(content) {
            if (content.userMessage.isNotBlank()){
                Toast.makeText(context, content.userMessage, Toast.LENGTH_SHORT).show()
                onToastShown()
            }
        }

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
                AuthTitleText(text = "Let's Login")
                Spacer(modifier = Modifier.height(16.dp))
                AuthDescriptionText(text = "And notes your idea")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    AuthOutlinedButtonText(text = stringResource(R.string.email))
                    Spacer(modifier = Modifier.height(12.dp))

                    AuthOutLinedTextField(
                        value = content.email,
                        onValueChange = updateEmail,
                        placeholderText = "sergey.mikhovich@gmail.com",
                        isError = content.contentState.isInvalidEmail,
                        supportingText = if (content.email.isBlank()) "Required field" else "Invalid email"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    AuthOutlinedButtonText(text = stringResource(R.string.password))
                    Spacer(modifier = Modifier.height(12.dp))

                    AuthOutLinedTextField(
                        value = content.password,
                        onValueChange = updatePassword,
                        placeholderText = "∗∗∗∗∗∗∗∗",
                        isError = content.contentState.isInvalidPassword,
                        supportingText = if (content.password.isBlank()) "Required field" else "Invalid password"
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                AuthButton(
                    onClick = onSignInClick,
                    text = stringResource(R.string.sign_in)
                )

                Spacer(modifier = Modifier.height(16.dp))
                WithTextCenteredDivider(centeredText = stringResource(R.string.or))
                Spacer(modifier = Modifier.height(16.dp))

                AuthenticationButton(
                    buttonText = R.string.sign_in_with_google,
                    onGetCredentialResponse = onSignInWithGoogle
                )

                Spacer(modifier = Modifier.height(12.dp))

                AuthOtherwiseButton(
                    text = "Don't have any account? Register here",
                    onClick = onSignUpClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    SignInContent(
        content = SignInState.Content(),
        updateEmail = {},
        updatePassword = {},
        onSignInClick = {},
        onSignInWithGoogle = {},
        onSignUpClick = {},
        onToastShown = {}
    )
}
