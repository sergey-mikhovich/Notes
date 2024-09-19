package com.sergeymikhovich.notes.core.permission

import android.content.Intent
import androidx.activity.ComponentActivity
import kotlinx.coroutines.flow.StateFlow

data class PermissionState(
    val notificationAccessHasDenied: Boolean = false,
    val hasNotificationAccess: Boolean = false,
    val notificationAccessHasRequested: Boolean = false
) {
    val hasAllPermissions: Boolean
        get() = hasNotificationAccess

    val hasAtLeastOneDeniedAccess: Boolean
        get() = notificationAccessHasDenied

    val allPermissionsRequested: Boolean
        get() = notificationAccessHasRequested
}

interface PermissionManager {
    val permissionState: StateFlow<PermissionState>
    fun createSettingsIntent(): Intent
    fun onPermissionChange(permissions: Map<String, Boolean>)
    fun isPermissionGranted(permission: String): Boolean
    suspend fun checkPermissions(activity: ComponentActivity)
}