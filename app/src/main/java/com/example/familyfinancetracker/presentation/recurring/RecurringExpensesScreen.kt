package com.example.familyfinancetracker.presentation.recurring

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.familyfinancetracker.data.model.RecurringExpense

@Composable
fun RecurringExpensesScreen() {

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val recurringExpenses = remember {
        mutableStateListOf<RecurringExpense>()
    }

    val totalRecurring = recurringExpenses.sumOf {
        it.amount.toIntOrNull() ?: 0
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                text = "Recurring Expenses",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Expense Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Monthly Amount") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    if (
                        title.isBlank() ||
                        amount.isBlank()
                    ) return@Button

                    recurringExpenses.add(
                        RecurringExpense(
                            id = recurringExpenses.size + 1,
                            title = title,
                            amount = amount
                        )
                    )

                    title = ""
                    amount = ""
                }
            ) {
                Text("Add Recurring Expense")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Monthly Recurring Total: ₹$totalRecurring",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(recurringExpenses.reversed()) { expense ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Text("Expense: ${expense.title}")

                    Text("Amount: ₹${expense.amount}")

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {
                            recurringExpenses.remove(expense)
                        }
                    ) {
                        Text("Delete")
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}