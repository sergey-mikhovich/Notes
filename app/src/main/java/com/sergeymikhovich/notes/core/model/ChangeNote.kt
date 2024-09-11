package com.sergeymikhovich.notes.core.model

data class ChangeNote(
    val id: String,
    val userId: String,
    val lastModifiedTime: Long,
    val deleted: Boolean
)
