package com.sergeymikhovich.notes.feature.auth.account_center

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.R
import com.sergeymikhovich.notes.core.common.navigation.composableTo
import com.sergeymikhovich.notes.core.model.User
import com.sergeymikhovich.notes.feature.auth.account_center.navigation.AccountCenterDirection

fun NavGraphBuilder.composableToAccountCenter() = composableTo(AccountCenterDirection) { AccountCenterScreen() }

@Composable
fun AccountCenterScreen(
    viewModel: AccountCenterViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsStateWithLifecycle()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(title = { Text(text = stringResource(R.string.account_center)) }) 
            
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            Card(modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 8.dp)) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)) {

                    if (!user.isAnonymous) {
                        Text(
                            text = String.format(stringResource(id = R.string.profile_email), user.email),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )
                    }

                    Text(
                        text = String.format(stringResource(R.string.profile_uid), user.id),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        text = String.format(stringResource(R.string.profile_provider), user.providerId),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            if (user.isAnonymous) {
                AccountCenterCard(
                    title = stringResource(R.string.sign_in),
                    icon = Icons.Filled.Face,
                    onCardClick = viewModel::toSignIn
                )

                AccountCenterCard(
                    title = stringResource(R.string.sign_up),
                    icon = Icons.Filled.AccountCircle,
                    onCardClick = viewModel::toSignUp
                )
            } else {
                ExitAppCard(onSignOutClick = viewModel::onSignOut)
                RemoveAccountCard(onRemoveAccountClick = viewModel::onDeleteAccount)
            }
        }
    }
}

@Composable
fun AccountCenterCard(
    title: String,
    icon: ImageVector,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp, 0.dp, 16.dp, 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = onCardClick)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) { Text(title) }
            Icon(icon, contentDescription = "Icon")
        }
    }
}

@Composable
fun ExitAppCard(onSignOutClick: () -> Unit) {
    var showExitAppDialog by remember { mutableStateOf(false) }

    AccountCenterCard(stringResource(R.string.sign_out),
        Icons.AutoMirrored.Filled.ExitToApp) {
        showExitAppDialog = true
    }

    if (showExitAppDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.sign_out_title)) },
            text = { Text(stringResource(R.string.sign_out_description)) },
            dismissButton = {
                Button(onClick = { showExitAppDialog = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            confirmButton = {
                Button(onClick = {
                    onSignOutClick()
                    showExitAppDialog = false
                }) {
                    Text(text = stringResource(R.string.sign_out))
                }
            },
            onDismissRequest = { showExitAppDialog = false }
        )
    }
}

@Composable
fun RemoveAccountCard(onRemoveAccountClick: () -> Unit) {
    var showRemoveAccDialog by remember { mutableStateOf(false) }

    AccountCenterCard(stringResource(R.string.delete_account), Icons.Filled.Delete) {
        showRemoveAccDialog = true
    }

    if (showRemoveAccDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.delete_account_title)) },
            text = { Text(stringResource(R.string.delete_account_description)) },
            dismissButton = {
                Button(onClick = { showRemoveAccDialog = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            confirmButton = {
                Button(onClick = {
                    onRemoveAccountClick()
                    showRemoveAccDialog = false
                }) {
                    Text(text = stringResource(R.string.delete_account))
                }
            },
            onDismissRequest = { showRemoveAccDialog = false }
        )
    }
}