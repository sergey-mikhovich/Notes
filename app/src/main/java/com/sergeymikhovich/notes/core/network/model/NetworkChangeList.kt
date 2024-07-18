package com.sergeymikhovich.notes.core.network.model

data class NetworkChangeList(
    val id: String = "",
    val changeListVersion: Int = -1,
    val isDelete: Boolean = false
) {

    companion object {
        const val COLLECTION_NAME = "ChangeList"

        const val ID = "id"
        const val CHANGE_LIST_VERSION = "changeListVersion"
        const val IS_DELETE = "isDelete"
    }
}
