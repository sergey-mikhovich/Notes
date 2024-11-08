package com.sergeymikhovich.notes.core.common.error_handling

data class TypingErrors(
    val isInvalidEmail: Boolean = false,
    val isInvalidPassword: Boolean = false,
    val arePasswordsNotMatch: Boolean = false
)