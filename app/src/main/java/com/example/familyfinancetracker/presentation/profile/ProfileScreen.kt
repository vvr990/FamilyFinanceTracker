package com.example.familyfinancetracker.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.google.firebase.auth.FirebaseAuth
import com.example.familyfinancetracker.data.remote.FirestoreSource

@Composable
fun ProfileScreen() {

    val auth = FirebaseAuth.getInstance()

    val email =
        auth.currentUser?.email ?: "No Email"

    val uid =
        auth.currentUser?.uid ?: "No UID"

    var totalIncome by remember {
        mutableStateOf(0)
    }

    var totalExpenses by remember {
        mutableStateOf(0)
    }

    var totalSavings by remember {
        mutableStateOf(0)
    }

    var familyMembersCount by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {

        FirestoreSource.getTotalIncome(
            onSuccess = {
                totalIncome = it
            },
            onError = {}
        )

        FirestoreSource.getTotalExpenses(
            onSuccess = {
                totalExpenses = it
            },
            onError = {}
        )

        FirestoreSource.getTotalSavings(
            onSuccess = {
                totalSavings = it
            },
            onError = {}
        )

        FirestoreSource.getFamilyMembers(
            onSuccess = {
                familyMembersCount = it.size
            },
            onError = {}
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Email")
                Text(email)

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text("User ID")
                Text(uid)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Total Income : ₹$totalIncome")

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text("Total Expenses : ₹$totalExpenses")

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text("Total Savings : ₹$totalSavings")

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Family Members : $familyMembersCount"
                )
            }
        }
    }
}