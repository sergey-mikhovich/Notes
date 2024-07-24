package com.sergeymikhovich.notes.core.network.model

data class NetworkNote(
    val id: String = "",
    val title: String = "",
    val description: String = ""
) {

    companion object {
        const val COLLECTION_NAME = "notes"
        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
    }
}
