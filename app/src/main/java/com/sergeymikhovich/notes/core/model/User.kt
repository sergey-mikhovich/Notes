package com.sergeymikhovich.notes.core.model

import android.net.Uri

data class User(
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUri: Uri? = Uri.EMPTY
)
