package com.sergeymikhovich.notes.feature.auth.sign_in

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.sergeymikhovich.notes.R
import com.sergeymikhovich.notes.app.ui.theme.NotesTheme
import com.sergeymikhovich.notes.app.ui.theme.Purple40
import com.sergeymikhovich.notes.core.common.navigation.composableTo
import com.sergeymikhovich.notes.feature.auth.AuthenticationButton
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInDirection
import kotlinx.coroutines.launch

fun NavGraphBuilder.composableToSignIn() = composableTo(SignInDirection) { SignInScreen() }

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel()
) {
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.mipmap.auth_image),
            contentDescription = "Auth image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
        )
        
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Purple40),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = email,
            onValueChange = viewModel::updateEmail ,
            placeholder = { Text(stringResource(R.string.email)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
        )

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Purple40),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = password,
            onValueChange = viewModel::updatePassword,
            placeholder = { Text(stringResource(R.string.password)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        Button(
            onClick = viewModel::onSignInClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple40),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                fontSize = 16.sp,
                modifier = Modifier.padding(0.dp, 6.dp)
            )
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp))

        Text(text = stringResource(R.string.or), fontSize = 16.sp, color = Purple40)

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp))

        AuthenticationButton(
            buttonText = R.string.sign_in_with_google,
            onGetCredentialResponse = viewModel::onSignInWithGoogle
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))

        TextButton(onClick = viewModel::toSignUp) {
            Text(text = stringResource(R.string.sign_up_description), fontSize = 16.sp, color = Purple40)
        }
    }
}

@Composable
fun SignInButton(
    onSignInClick: () -> Unit,
    onSignedInSuccessfully: (String) -> Unit
) {

    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)
    val coroutineScope = rememberCoroutineScope()

    Button(onClick = {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(context, request)
                handleSignIn(result, onSignedInSuccessfully)
                Log.i("GoogleSignInScreen", "Sign in invoked")
                onSignInClick.invoke()
            } catch (e: GetCredentialException) {
                Log.e("GoogleSignInScreen", "$e")
            }
        }
    }) {
        Text("Sign in with Google")
    }
}

fun handleSignIn(result: GetCredentialResponse, onSignedInSuccessfully: (String) -> Unit) {
    when (val credential = result.credential) {
        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)

                    Log.i("GoogleSignInScreen", "You are signed in")
                    onSignedInSuccessfully(googleIdTokenCredential.idToken)
                    /*
                    val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport.Builder().build(), GsonFactory.getDefaultInstance())
                        .setAudience(Collections.singletonList("385767106267-i037l12ma59e08rqcsdo891dlnuk2m36.apps.googleusercontent.com"))
                        .build()
                    val idToken = verifier.verify(googleIdToken)

                    if (idToken != null) {
                        Log.i("GoogleSignInScreen", "You are signed in")
                    } else {
                        Log.e("GoogleSignInScreen", "Invalid ID token")
                    }
                     */

                } catch (e: GoogleIdTokenParsingException) {
                    Log.i("GoogleSignInScreen", "You are GoogleIdTokenParsingException")
                }
            }
        }
        else -> {
            Log.e("GoogleSignInScreen", "Unexpected type of credential")
        }
    }
}
