package com.sergeymikhovich.notes.core.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "change_note",
    indices = [Index("userId")]
)
data class ChangeNoteEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val lastModifiedTime: Long,
    val deleted: Boolean
)
