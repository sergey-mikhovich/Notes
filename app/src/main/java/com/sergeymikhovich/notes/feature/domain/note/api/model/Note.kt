package com.sergeymikhovich.notes.feature.domain.note.api.model

class Note(
    val id: Long,
    val title: String,
    val description: String
) {
    constructor(title: String, description: String): this(0L, title, description)
}