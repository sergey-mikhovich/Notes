package com.sergeymikhovich.notes.core.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Composable
fun rememberMultiplePermissionsState(
    permissions: List<String>,
    onPermissionsResult: (Map<String, Boolean>) -> Unit = {}
): MultiplePermissionsState {
    return rememberMutableMultiplePermissionsState(permissions, onPermissionsResult)
}

@Stable
interface MultiplePermissionsState {
    val permissions: List<PermissionState>
    val revokedPermissions: List<PermissionState>
    val allPermissionsGranted: Boolean
    val shouldShowRationale: Boolean
    fun launchMultiplePermissionsRequest()
}