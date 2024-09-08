package com.sergeymikhovich.notes.core.data.mapper

import android.net.Uri
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.sergeymikhovich.notes.core.common.mapping.Mapper
import com.sergeymikhovich.notes.core.model.User

class AccountMapper {

    val firebaseToDomainUser = Mapper<FirebaseUser?, User> { firebaseUser ->
        if (firebaseUser == null)
            return@Mapper User()

        User(
            id = firebaseUser.uid,
            email = firebaseUser.email ?: "",
            displayName = firebaseUser.displayName ?: "",
            photoUri = Uri.parse(firebaseUser.photoUrl.toString().replaceAfterLast("=", "").removeSuffix("=")),
            providerId = firebaseUser.providerData
                .firstOrNull { it.providerId != FirebaseAuthProvider.PROVIDER_ID }?.providerId
                ?: FirebaseAuthProvider.PROVIDER_ID,
            isAnonymous = firebaseUser.isAnonymous
        )
    }
}