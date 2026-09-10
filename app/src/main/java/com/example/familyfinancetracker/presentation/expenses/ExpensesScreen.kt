package com.example.familyfinancetracker.presentation.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.familyfinancetracker.data.model.Expense
import com.example.familyfinancetracker.data.remote.FirestoreSource

import androidx.compose.runtime.LaunchedEffect


@Composable
fun ExpensesScreen() {

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    var message by remember { mutableStateOf("") }

    var editingExpense by remember {
        mutableStateOf<Expense?>(null)
    }

    val expenses = remember {
        mutableStateListOf<Expense>()
    }

    LaunchedEffect(Unit) {

        FirestoreSource.getExpenses(
            onSuccess = { firestoreExpenses ->

                expenses.clear()
                expenses.addAll(firestoreExpenses)
            },
            onError = {
                println(it)
            }
        )
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
                    ) {
                        message = "Please fill all fields"
                        return@Button
                    }

                    // UPDATE EXISTING EXPENSE
                    if (editingExpense != null) {

                        val updatedExpense = editingExpense!!.copy(
                            title = title,
                            amount = amount,
                            category = category
                        )

                        FirestoreSource.updateExpense(
                            expense = updatedExpense,

                            onSuccess = {

                                FirestoreSource.getExpenses(

                                    onSuccess = { firestoreExpenses ->

                                        expenses.clear()
                                        expenses.addAll(firestoreExpenses)

                                        title = ""
                                        amount = ""
                                        category = ""

                                        editingExpense = null

                                        message = "Expense Updated Successfully"
                                    },

                                    onError = {
                                        message = it
                                    }
                                )
                            },

                            onError = {
                                message = it
                            }
                        )

                        return@Button
                    }

                    // ADD NEW EXPENSE
                    val expense = Expense(
                        id = expenses.size + 1,
                        title = title,
                        amount = amount,
                        category = category
                    )

                    FirestoreSource.addExpense(
                        expense = expense,

                        onSuccess = {

                            FirestoreSource.getExpenses(

                                onSuccess = { firestoreExpenses ->

                                    expenses.clear()
                                    expenses.addAll(firestoreExpenses)

                                    title = ""
                                    amount = ""
                                    category = ""

                                    message = "Expense Saved Successfully"
                                },

                                onError = {
                                    message = it
                                }
                            )
                        },

                        onError = {
                            message = it
                        }
                    )
                }
            ) {
                Text(
                    if (editingExpense == null)
                        "Add Expense"
                    else
                        "Update Expense"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (message.isNotEmpty()) {
                Text(message)
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

                            if (expense.documentId.isBlank()) {
                                return@Button
                            }

                            FirestoreSource.deleteExpense(
                                documentId = expense.documentId,
                                onSuccess = {
                                    expenses.remove(expense)
                                },
                                onError = {
                                    println(it)
                                }
                            )
                        }
                    ) {
                        Text("Delete")
                    }
                    Button(
                        onClick = {

                            editingExpense = expense

                            title = expense.title
                            amount = expense.amount
                            category = expense.category

                            message = "Editing Expense..."
                        }
                    ) {
                        Text("Edit")
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}