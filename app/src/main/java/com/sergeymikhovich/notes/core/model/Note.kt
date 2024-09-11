package com.sergeymikhovich.notes.core.model

import java.util.UUID

data class Note(
    val id: String,
    val userId: String,
    val title: String,
    val description: String
) {
    constructor(userId: String, title: String, description: String):
            this(UUID.randomUUID().toString(), userId, title, description)

    fun isEmpty() = title.isEmpty() && description.isEmpty()
}