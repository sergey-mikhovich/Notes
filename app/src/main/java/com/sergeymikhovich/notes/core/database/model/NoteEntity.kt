package com.sergeymikhovich.notes.core.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "note",
    indices = [Index("userId")]
)
class NoteEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val title: String,
    val description: String
)