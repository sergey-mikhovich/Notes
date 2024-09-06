package com.sergeymikhovich.notes.core.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

fun FirebaseAuth.getCurrentUserId() = Firebase.auth.currentUser?.uid ?: ""