package com.sergeymikhovich.notes.core.common.error_handling

import com.sergeymikhovich.notes.core.common.error_handling.TypingError.InvalidEmail
import com.sergeymikhovich.notes.core.common.error_handling.TypingError.InvalidPassword
import com.sergeymikhovich.notes.core.common.error_handling.TypingError.PasswordsNotMatch

sealed interface TypingError {
    data object InvalidEmail : TypingError
    data object InvalidPassword : TypingError
    data object PasswordsNotMatch : TypingError
}

fun TypingError?.isInvalidEmail() = this is InvalidEmail
fun Collection<TypingError>.isInvalidEmail() = this.contains(InvalidEmail)

fun TypingError?.isInvalidPassword() = this is InvalidPassword
fun Collection<TypingError>.isInvalidPassword() = this.contains(InvalidPassword)

fun TypingError?.arePasswordsNotMatch() = this is PasswordsNotMatch
fun Collection<TypingError>.arePasswordsNotMatch() = this.contains(PasswordsNotMatch)