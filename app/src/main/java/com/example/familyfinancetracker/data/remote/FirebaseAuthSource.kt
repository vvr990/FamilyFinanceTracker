package com.example.familyfinancetracker.data.remote

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthSource {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(
            email,
            password
        )
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Unknown Error")
            }
    }

    fun loginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        auth.signInWithEmailAndPassword(
            email,
            password
        )
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Login Failed")
            }
    }
}