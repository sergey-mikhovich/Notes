package com.sergeymikhovich.notes.core.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Composable
fun rememberPermissionState(
    permission: String,
    onPermissionResult: (Boolean) -> Unit = {}
): PermissionState {
    return rememberMutablePermissionState(permission, onPermissionResult)
}

@Stable
interface PermissionState {
    val permission: String
    var status: PermissionStatus
    fun launchPermissionRequest()
}