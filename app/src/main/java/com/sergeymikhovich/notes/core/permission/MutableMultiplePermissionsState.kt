package com.sergeymikhovich.notes.core.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberMutableMultiplePermissionsState(
    permissions: List<String>,
    onPermissionsResult: (Map<String, Boolean>) -> Unit = {}
): MultiplePermissionsState {

    val mutablePermissions = rememberMutablePermissionsState(permissions)

    PermissionsLifecycleCheckerEffect(mutablePermissions)

    val multiplePermissionsState = remember(permissions) {
        MutableMultiplePermissionsState(mutablePermissions)
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        multiplePermissionsState.updatePermissionsStatus(it)
        onPermissionsResult(it)
    }

    DisposableEffect(multiplePermissionsState, launcher) {
        multiplePermissionsState.launcher = launcher
        onDispose { multiplePermissionsState.launcher = null  }
    }

    return multiplePermissionsState
}

@Composable
fun rememberMutablePermissionsState(
    permissions: List<String>
): List<MutablePermissionState> {
    val context = LocalContext.current
    val activity = context.findActivity()

    val mutablePermissions: List<MutablePermissionState> = remember(permissions) {
        permissions.map { MutablePermissionState(it, context, activity) }
    }

    for (permission in mutablePermissions) {
        key(permission.permission) {
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {
                permission.refreshPermissionStatus()
            }

            DisposableEffect(launcher) {
                permission.launcher = launcher
                onDispose { permission.launcher = null }
            }
        }
    }

    return mutablePermissions
}

@Stable
class MutableMultiplePermissionsState(
    private val mutablePermissions: List<MutablePermissionState>
) : MultiplePermissionsState {

    var launcher: ActivityResultLauncher<Array<String>>? = null

    override val permissions: List<PermissionState> = mutablePermissions

    override val revokedPermissions: List<PermissionState> by derivedStateOf {
        permissions.filter { !it.status.isGranted }
    }

    override val allPermissionsGranted: Boolean by derivedStateOf {
        permissions.all { it.status.isGranted } || revokedPermissions.isEmpty()
    }

    override val shouldShowRationale: Boolean by derivedStateOf {
        permissions.any { it.status.shouldShowRationale }
    }

    override fun launchMultiplePermissionsRequest() {
        launcher?.launch(
            permissions.map { it.permission }.toTypedArray()
        ) ?: throw IllegalStateException("ActivityResultLauncher cannot be null")
    }

    fun updatePermissionsStatus(permissionsStatus: Map<String, Boolean>) {
        for (permission in permissionsStatus.keys) {
            mutablePermissions.firstOrNull { it.permission == permission }?.apply {
                permissionsStatus[permission]?.let {
                    refreshPermissionStatus()
                }
            }
        }
    }
}