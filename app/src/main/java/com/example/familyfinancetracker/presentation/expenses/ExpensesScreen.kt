package com.example.familyfinancetracker.presentation.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.familyfinancetracker.data.model.Expense

@Composable
fun ExpensesScreen() {

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    val expenses = remember {
        mutableStateListOf<Expense>()
    }

    val totalExpense = expenses.sumOf {
        it.amount.toIntOrNull() ?: 0
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                text = "Expenses",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    if (
                        title.isBlank() ||
                        amount.isBlank() ||
                        category.isBlank()
                    ) return@Button

                    expenses.add(
                        Expense(
                            id = expenses.size + 1,
                            title = title,
                            amount = amount,
                            category = category
                        )
                    )

                    title = ""
                    amount = ""
                    category = ""
                }
            ) {
                Text("Add Expense")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Total Expenses: ₹$totalExpense",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Number of Expenses: ${expenses.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(expenses.reversed()) { expense ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Text("Title: ${expense.title}")

                    Text("Amount: ₹${expense.amount}")

                    Text("Category: ${expense.category}")

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {
                            expenses.remove(expense)
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