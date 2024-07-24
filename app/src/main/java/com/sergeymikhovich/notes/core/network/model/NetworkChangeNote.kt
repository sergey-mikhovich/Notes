package com.sergeymikhovich.notes.core.network.model

data class NetworkChangeNote(
    val id: String = "",
    val lastModifiedTime: Long = -1,
    val deleted: Boolean = false
) {

    companion object {
        const val COLLECTION_NAME = "changeNotes"
        const val ID = "id"
        const val LAST_MODIFIED_TIME = "lastModifiedTime"
        const val DELETED = "deleted"
    }
}
