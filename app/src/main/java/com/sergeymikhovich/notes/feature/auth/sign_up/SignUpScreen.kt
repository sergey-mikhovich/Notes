package com.sergeymikhovich.notes.feature.auth.sign_up

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.Credential
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.R
import com.sergeymikhovich.notes.app.ui.theme.Purple40
import com.sergeymikhovich.notes.core.common.navigation.composableTo
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
            Text(
                text = "Register",
                fontSize = TextUnit(32F, TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                lineHeight = TextUnit(1.2F, TextUnitType.Em),
                color = Color(0xFF403B36)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "And start taking notes",
                fontSize = TextUnit(16F, TextUnitType.Sp),
                lineHeight = TextUnit(1.4F, TextUnitType.Em),
                color = Color(0xFF595550)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.email),
                    color = Color(0xFF403B36),
                    fontWeight = FontWeight.Medium,
                    lineHeight = TextUnit(1.4F, TextUnitType.Em),
                    fontSize = TextUnit(16F, TextUnitType.Sp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(width = 1.dp, color = Color(0xFFF2E5D5)),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFFFDFA),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    value = email,
                    onValueChange = updateEmail ,
                    placeholder = {
                        Text(
                            text = "sergey.mikhovich@gmail.com",
                            lineHeight = TextUnit(1.4F, TextUnitType.Em),
                            fontWeight = FontWeight.Normal,
                            fontSize = TextUnit(16F, TextUnitType.Sp),
                            color = Color(0xFFC8C5CB),
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.password),
                    color = Color(0xFF403B36),
                    fontWeight = FontWeight.Medium,
                    lineHeight = TextUnit(1.4F, TextUnitType.Em),
                    fontSize = TextUnit(16F, TextUnitType.Sp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(width = 1.dp, color = Color(0xFFF2E5D5)),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFFFDFA),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    value = password,
                    onValueChange = updatePassword ,
                    placeholder = {
                        Text(
                            text = "********",
                            lineHeight = TextUnit(1.4F, TextUnitType.Em),
                            fontWeight = FontWeight.Normal,
                            fontSize = TextUnit(16F, TextUnitType.Sp),
                            color = Color(0xFFC8C5CB),
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.confirm_password),
                    color = Color(0xFF403B36),
                    fontWeight = FontWeight.Medium,
                    lineHeight = TextUnit(1.4F, TextUnitType.Em),
                    fontSize = TextUnit(16F, TextUnitType.Sp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(width = 1.dp, color = Color(0xFFF2E5D5)),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFFFDFA),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    value = confirmPassword,
                    onValueChange = updateConfirmPassword ,
                    placeholder = {
                        Text(
                            text = "********",
                            lineHeight = TextUnit(1.4F, TextUnitType.Em),
                            fontWeight = FontWeight.Normal,
                            fontSize = TextUnit(16F, TextUnitType.Sp),
                            color = Color(0xFFC8C5CB),
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = onSignUpClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFD9614C)
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp
                )
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    lineHeight = TextUnit(1.4F, TextUnitType.Em),
                    fontSize = TextUnit(16F, TextUnitType.Sp),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    modifier = Modifier.wrapContentWidth(),
                    text = stringResource(R.string.or),
                    fontSize = 12.sp, color = Color(0xFF827D89)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Divider(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            AuthenticationButton(
                buttonText = R.string.sign_up_with_google,
                onGetCredentialResponse = onSignUpWithGoogleClick
            )

//            Button(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(54.dp),
//                shape = RoundedCornerShape(100.dp),
//                border = BorderStroke(1.dp, Color(0xFFC8C5CB)),
//                onClick = onSignInWithGoogle,
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = Color.White
//                ),
//                elevation = ButtonDefaults.elevation(
//                    defaultElevation = 0.dp
//                )
//            ) {
//                Text(
//                    text = stringResource(R.string.sign_in_with_google),
//                    lineHeight = TextUnit(1.4F, TextUnitType.Em),
//                    fontSize = TextUnit(16F, TextUnitType.Sp),
//                    color = Color(0xFF180E25)
//                )
//            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(),
                shape = RoundedCornerShape(12.dp),
                onClick = onSignInClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp
                )
            ) {
                Text(
                    text = "Already have an account? Login here",
                    lineHeight = TextUnit(1.4F, TextUnitType.Em),
                    fontSize = TextUnit(16F, TextUnitType.Sp),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFD9614C)
                )
            }
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