package com.sergeymikhovich.notes.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Note(
    val id: String,
    val userId: String,
    val title: String,
    val description: String
): Parcelable {
    constructor(userId: String, title: String, description: String):
            this(UUID.randomUUID().toString(), userId, title, description)

    fun isEmpty() = title.isEmpty() && description.isEmpty()
}