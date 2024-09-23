package com.sergeymikhovich.notes.feature.auth.account_center

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import coil.compose.AsyncImage
import com.sergeymikhovich.notes.R
import com.sergeymikhovich.notes.core.common.navigation.composableTo
import com.sergeymikhovich.notes.core.design_system.component.AccountCenterButton
import com.sergeymikhovich.notes.core.model.User
import com.sergeymikhovich.notes.feature.auth.account_center.navigation.AccountCenterDirection

fun NavGraphBuilder.composableToAccountCenter() = composableTo(AccountCenterDirection) { AccountCenterScreen() }

@Composable
fun AccountCenterScreen(
    viewModel: AccountCenterViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsStateWithLifecycle()

    AccountCenterContent(
        user = user,
        onSignOutClick = viewModel::onSignOut,
        onDeleteAccountClick = viewModel::onDeleteAccount
    )
}

@Composable
private fun AccountCenterContent(
    user: User,
    onSignOutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = Color(0xFFF8EEE2))
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier.height(24.dp)
            )

            AsyncImage(
                model = user.photoUri,
                contentDescription = "",
                placeholder = painterResource(id = R.drawable.google_g),
                error = painterResource(id = R.drawable.google_g),
                fallback = painterResource(id = R.drawable.google_g),
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )

            Text(
                text = user.displayName.ifBlank { "Unknown" },
                fontSize = TextUnit(28f, TextUnitType.Sp),
                fontWeight = FontWeight.Black,
                color = Color(0xFF403B36)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = user.email,
                fontSize = TextUnit(16f, TextUnitType.Sp),
                color = Color(0xFF595550)
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Divider()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "APP SETTINGS",
            fontSize = TextUnit(10f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            color = Color(0xFF595550)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            AccountCenterButton(
                text = "Edit profile",
                textColor = Color(0xFF595550),
                startIcon = Icons.Filled.Edit,
                endIcon = Icons.AutoMirrored.Sharp.KeyboardArrowRight,
                onClick = {}
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 16.dp
                            ),
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "",
                        tint = Color(0xFFD9614C)
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        text = "Edit profile",
                        fontWeight = FontWeight.Medium,
                        fontSize = TextUnit(16f, TextUnitType.Sp),
                        color = Color(0xFF595550)
                    )
                }
                Icon(
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 16.dp
                        ),
                    imageVector = Icons.AutoMirrored.Sharp.KeyboardArrowRight,
                    contentDescription = "",
                    tint = Color(0xFF595550)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider()

            Spacer(modifier = Modifier.height(8.dp))

            AccountCenterButton(
                text = "Sign out",
                textColor = Color(0xFFD9614C),
                startIcon = Icons.AutoMirrored.Filled.ExitToApp,
                onClick = onSignOutClick
            )

            AccountCenterButton(
                text = "Delete account",
                textColor = Color(0xFFD9614C),
                startIcon = Icons.Filled.Delete,
                onClick = onDeleteAccountClick
            )
        }
    }
}

@Preview
@Composable
private fun AccountCenterScreenPreview() {
    AccountCenterContent(
        user = User(
            id = "123sdfdf",
            email = "sergey.mikhovich@gmail.com",
            displayName = "Sergey Mikhovich"
        ),
        onSignOutClick = {},
        onDeleteAccountClick = {}
    )
}