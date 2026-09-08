package com.example.familyfinancetracker.presentation.savings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SavingsScreen() {

    val totalIncome = 95000
    val totalExpenses = 52000

    val savings = totalIncome - totalExpenses

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Savings Summary",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Total Income : ₹$totalIncome")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Total Expenses : ₹$totalExpenses")

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Savings : ₹$savings",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}