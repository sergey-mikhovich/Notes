package com.sergeymikhovich.notes.feature.auth

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.sergeymikhovich.notes.R
import com.sergeymikhovich.notes.app.ui.theme.Purple40
import kotlinx.coroutines.launch

@Composable
fun AuthenticationButton(
    buttonText: Int,
    onGetCredentialResponse: (Credential) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFC8C5CB)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        ),
        onClick = {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            coroutineScope.launch {
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = context
                    )

                    onGetCredentialResponse(result.credential)
                } catch (e: GetCredentialException) {
                    Log.d("AuthenticationButton", e.message.orEmpty())
                }
            }
        },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.google_g),
            modifier = Modifier.padding(horizontal = 16.dp),
            contentDescription = "Google logo"
        )

        Text(
            text = stringResource(buttonText),
            fontSize = 16.sp,
            modifier = Modifier.padding(0.dp, 6.dp),
            color = Color(0xFF403B36)
        )
    }
}

@Composable
@Preview
fun AuthenticationButtonPreview() {
    AuthenticationButton(
        buttonText = 1,
        onGetCredentialResponse = {}
    )
}