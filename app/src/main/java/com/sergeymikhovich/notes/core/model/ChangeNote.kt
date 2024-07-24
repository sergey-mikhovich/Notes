package com.sergeymikhovich.notes.core.model

data class ChangeNote(
    val id: String,
    val lastModifiedTime: Long,
    val deleted: Boolean
)
