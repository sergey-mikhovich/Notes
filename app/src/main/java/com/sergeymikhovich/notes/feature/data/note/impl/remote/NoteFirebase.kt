package com.sergeymikhovich.notes.feature.data.note.impl.remote

data class NoteFirebase(
    val id: String = "",
    val title: String = "",
    val description: String = ""
) {

    companion object {
        const val COLLECTION_NAME = "Notes"

        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
    }
}
