package com.sergeymikhovich.notes.core.model

import java.util.UUID

data class Note(
    val id: String,
    val title: String,
    val description: String
) {
    constructor(title: String, description: String): this(UUID.randomUUID().toString(), title, description)

    fun isEmpty() = title.isEmpty() && description.isEmpty()
}