package com.sergeymikhovich.notes.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "change_note")
data class ChangeNoteEntity(
    @PrimaryKey
    val id: String,
    val lastModifiedTime: Long,
    val deleted: Boolean
)
