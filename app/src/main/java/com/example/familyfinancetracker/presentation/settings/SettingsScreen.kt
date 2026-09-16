package com.example.familyfinancetracker.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController

import com.google.firebase.auth.FirebaseAuth
import com.example.familyfinancetracker.navigation.Screen

@Composable
fun SettingsScreen(
    navController: NavController
) {

    val auth = FirebaseAuth.getInstance()

    val email =
        auth.currentUser?.email ?: "No Email"

    var message by remember {
        mutableStateOf("")
    }

    var showLogoutDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Logged In User",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(email)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                FirebaseAuth
                    .getInstance()
                    .sendPasswordResetEmail(email)
                    .addOnSuccessListener {

                        message =
                            "Password reset email sent successfully"
                    }
                    .addOnFailureListener {

                        message =
                            it.message ?: "Error"
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Reset Password")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {

                message =
                    """
                    Family Finance Tracker
                    
                    Version : 1.0
                    
                    Built Using:
                    • Kotlin
                    • Jetpack Compose
                    • Firebase Authentication
                    • Firebase Firestore
                    """.trimIndent()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("About App")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {

                showLogoutDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Logout")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (message.isNotEmpty()) {

            Text(
                text = message
            )
        }
    }

    if (showLogoutDialog) {

        AlertDialog(

            onDismissRequest = {
                showLogoutDialog = false
            },

            title = {
                Text("Logout")
            },

            text = {
                Text(
                    "Are you sure you want to logout?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        auth.signOut()

                        navController.navigate(
                            Screen.Login.route
                        ) {

                            popUpTo(0)
                        }
                    }
                ) {

                    Text("Logout")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {

                        showLogoutDialog = false
                    }
                ) {

                    Text("Cancel")
                }
            }
        )
    }
}