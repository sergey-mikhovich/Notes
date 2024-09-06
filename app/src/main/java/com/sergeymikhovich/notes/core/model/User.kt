package com.sergeymikhovich.notes.core.model

data class User(
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val providerId: String = "",
    val isAnonymous: Boolean = true
)
