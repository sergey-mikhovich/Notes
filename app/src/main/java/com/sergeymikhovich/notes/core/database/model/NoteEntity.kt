package com.sergeymikhovich.notes.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
class NoteEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String
)