package com.sergeymikhovich.notes.core.permission

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Stable
sealed interface PermissionStatus {
    data object Granted: PermissionStatus
    data class Denied(val shouldShowRationale: Boolean): PermissionStatus
}

val PermissionStatus.isGranted: Boolean
    get() = this == PermissionStatus.Granted

val PermissionStatus.shouldShowRationale: Boolean
    get() = when (this) {
        is PermissionStatus.Granted -> false
        is PermissionStatus.Denied -> shouldShowRationale
    }

@Composable
fun PermissionLifecycleCheckerEffect(
    permissionState: MutablePermissionState,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_RESUME
) {
    val permissionCheckerObserver = remember(permissionState) {
        LifecycleEventObserver { _, event ->
            if (event == lifecycleEvent) {
                if (permissionState.status != PermissionStatus.Granted) {
                    permissionState.refreshPermissionStatus()
                }
            }
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle, permissionCheckerObserver) {
        lifecycle.addObserver(permissionCheckerObserver)
        onDispose { lifecycle.removeObserver(permissionCheckerObserver) }
    }
}

@Composable
fun PermissionsLifecycleCheckerEffect(
    permissions: List<MutablePermissionState>,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_RESUME
) {
    val permissionsCheckerObserver = remember(permissions) {
        LifecycleEventObserver { _ , event ->
            if (event == lifecycleEvent) {
                for (permission in permissions) {
                    if (permission.status != PermissionStatus.Granted) {
                        permission.refreshPermissionStatus()
                    }
                }
            }
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle, permissionsCheckerObserver) {
        lifecycle.addObserver(permissionsCheckerObserver)
        onDispose { lifecycle.removeObserver(permissionsCheckerObserver) }
    }
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity)
            return context

        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}

fun Context.checkPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

fun Activity.shouldShowRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}

fun Context.goToAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}