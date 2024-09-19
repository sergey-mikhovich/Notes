package com.sergeymikhovich.notes.core.permission

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PermissionManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): PermissionManager {

    private val _permissionState = MutableStateFlow(PermissionState())
    override val permissionState = _permissionState.asStateFlow()

    override fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    override fun onPermissionChange(permissions: Map<String, Boolean>) {
        permissions.forEach { entry ->
            val permission = entry.key
            val isGranted = entry.value

            when(permission) {
                POST_NOTIFICATIONS -> {
                    _permissionState.update {
                        it.copy(
                            hasNotificationAccess = isGranted,
                            notificationAccessHasDenied = !isGranted,
                            notificationAccessHasRequested = true
                        )
                    }
                }
                else -> {}
            }
        }
    }

    override fun createSettingsIntent() =
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.fromParts("package", context.packageName, null)
        }

    override suspend fun checkPermissions(activity: ComponentActivity) {
        _permissionState.update {
            it.copy(
                hasNotificationAccess = if (isPermissionNeededForNotifications()) {
                    isPermissionGranted(POST_NOTIFICATIONS)
                } else true,
                notificationAccessHasDenied = if (isPermissionNeededForNotifications()) {
                    !shouldShowRequestPermissionRationale(activity, POST_NOTIFICATIONS)
                } else false
            )
        }
    }

    private fun shouldShowRequestPermissionRationale(
        activity: ComponentActivity,
        permission: String
    ): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            activity.shouldShowRequestPermissionRationale(permission)
    }

    private fun isPermissionNeededForNotifications(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }
}