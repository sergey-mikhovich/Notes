package com.sergeymikhovich.notes.core.permission

import kotlinx.coroutines.flow.StateFlow

interface PermissionManager {
    val deniedPermissionsState: StateFlow<Set<String>>
    fun markAsPermanentlyDenied(permission: Set<String>)
    fun isPermanentlyDenied(permission: Set<String>): Boolean
}