package com.example.familyfinancetracker.presentation.recurring

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.runtime.LaunchedEffect

import com.example.familyfinancetracker.data.remote.FirestoreSource
import com.example.familyfinancetracker.data.DashboardRefresh

import androidx.compose.foundation.layout.statusBarsPadding

import com.example.familyfinancetracker.data.model.RecurringExpense

@Composable
fun RecurringExpensesScreen() {

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var message by remember {
        mutableStateOf("")
    }

    var editingExpense by remember {
        mutableStateOf<RecurringExpense?>(null)
    }

    val recurringExpenses = remember {
        mutableStateListOf<RecurringExpense>()
    }

    val totalRecurring = recurringExpenses.sumOf {
        it.amount.toIntOrNull() ?: 0
    }

    LaunchedEffect(Unit) {

        FirestoreSource.getRecurringExpenses(

            onSuccess = { firestoreExpenses ->

                recurringExpenses.clear()
                recurringExpenses.addAll(firestoreExpenses)
            },

            onError = {
                println(it)
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {

        item {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "🔁 Recurring Expenses",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(4.dp))

//                    Text(
//                        text = "Track rent, EMI, subscriptions and other fixed monthly expenses",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

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
                    ) {
                        message = "Please fill all fields"
                        return@Button
                    }

                    if (editingExpense != null) {

                        val updatedExpense =
                            editingExpense!!.copy(
                                title = title,
                                amount = amount
                            )

                        FirestoreSource.updateRecurringExpense(
                            recurringExpense = updatedExpense,

                            onSuccess = {

                                DashboardRefresh.triggerRefresh()

                                FirestoreSource.getRecurringExpenses(

                                    onSuccess = { firestoreExpenses ->

                                        recurringExpenses.clear()
                                        recurringExpenses.addAll(
                                            firestoreExpenses
                                        )

                                        editingExpense = null

                                        title = ""
                                        amount = ""

                                        message =
                                            "Recurring Expense Updated"
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

                    val recurringExpense =
                        RecurringExpense(
                            id = recurringExpenses.size + 1,
                            title = title,
                            amount = amount
                        )

                    FirestoreSource.addRecurringExpense(
                        recurringExpense = recurringExpense,

                        onSuccess = {

                            DashboardRefresh.triggerRefresh()

                            FirestoreSource.getRecurringExpenses(

                                onSuccess = { firestoreExpenses ->

                                    recurringExpenses.clear()
                                    recurringExpenses.addAll(
                                        firestoreExpenses
                                    )

                                    title = ""
                                    amount = ""

                                    message =
                                        "Recurring Expense Saved"
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
            )
            {
                Text(
                    if (editingExpense == null)
                        "Add Recurring Expense"
                    else
                        "Update Recurring Expense"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (message.isNotEmpty()) {
                Text(message)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Card(
                    modifier = Modifier.weight(1f)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text("🔄 Recurring")

                        Text(
                            text = "₹$totalRecurring",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text("📋 Items")

                        Text(
                            text = "${recurringExpenses.size}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        if (recurringExpenses.isEmpty()) {

            item {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "🔄 No Recurring Expenses",
                            style =
                                MaterialTheme.typography.titleMedium
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text =
                                "Add rent, EMI, subscriptions and other recurring expenses."
                        )
                    }
                }
            }
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
                            if (expense.documentId.isBlank()) {
                                return@Button
                            }

                            FirestoreSource.deleteRecurringExpense(
                                documentId = expense.documentId,

                                onSuccess = {
                                    DashboardRefresh.triggerRefresh()
                                    recurringExpenses.remove(expense)
                                },

                                onError = {
                                    println(it)
                                }
                            )                        }
                    ) {
                        Text("Delete")
                    }
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {

                            editingExpense = expense

                            title = expense.title
                            amount = expense.amount

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