package com.sergeymikhovich.notes.feature.domain.note.api.model

import java.util.UUID

data class Note(
    val id: String,
    val title: String,
    val description: String
) {
    constructor(title: String, description: String): this(UUID.randomUUID().toString(), title, description)
}