package com.sergeymikhovich.notes.core.permission

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManagerImpl @Inject constructor(): PermissionManager {

    private val _deniedPermissionsState: MutableStateFlow<Set<String>> = MutableStateFlow(setOf())
    override val deniedPermissionsState = _deniedPermissionsState.asStateFlow()

    override fun markAsPermanentlyDenied(permission: Set<String>) {
        _deniedPermissionsState.update {
            val newSet = it + permission
            newSet
        }
    }

    override fun isPermanentlyDenied(permission: Set<String>): Boolean {
        return _deniedPermissionsState.value.containsAll(permission)
    }
}