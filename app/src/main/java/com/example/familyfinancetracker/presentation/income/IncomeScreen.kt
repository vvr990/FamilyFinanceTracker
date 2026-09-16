package com.example.familyfinancetracker.presentation.income

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

import com.example.familyfinancetracker.data.model.Income

@Composable
fun IncomeScreen() {

    var title by remember { mutableStateOf("") }
    var source by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var message by remember { mutableStateOf("") }

    var editingIncome by remember {
        mutableStateOf<Income?>(null)
    }

    val incomes = remember {
        mutableStateListOf<Income>()
    }
    LaunchedEffect(Unit) {

        FirestoreSource.getIncome(
            onSuccess = { firestoreIncome ->

                incomes.clear()
                incomes.addAll(firestoreIncome)
            },
            onError = {
                println(it)
            }
        )
    }

    val totalIncome = incomes.sumOf {
        it.amount.toIntOrNull() ?: 0
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                text = "Income",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = {
                    Text("Title")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = source,
                onValueChange = {
                    source = it
                },
                label = {
                    Text("Income Source")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                },
                label = {
                    Text("Amount")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    if (
                        title.isBlank() ||
                        source.isBlank() ||
                        amount.isBlank()
                    ) {
                        message = "Please fill all fields"
                        return@Button
                    }

                    if (editingIncome == null) {

                        val income = Income(
                            id = incomes.size + 1,
                            title = title,
                            source = source,
                            amount = amount
                        )

                        FirestoreSource.addIncome(
                            income = income,

                            onSuccess = {
                                DashboardRefresh.triggerRefresh()

                                FirestoreSource.getIncome(

                                    onSuccess = { firestoreIncome ->

                                        incomes.clear()
                                        incomes.addAll(firestoreIncome)

                                        title = ""
                                        source = ""
                                        amount = ""

                                        message = "Income Saved Successfully"
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
                    else {

                        val updatedIncome = editingIncome!!.copy(
                            title = title,
                            source = source,
                            amount = amount
                        )

                        FirestoreSource.updateIncome(
                            income = updatedIncome,

                            onSuccess = {
                                DashboardRefresh.triggerRefresh()

                                val index = incomes.indexOfFirst {
                                    it.documentId == updatedIncome.documentId
                                }

                                if (index != -1) {
                                    incomes[index] = updatedIncome
                                }

                                editingIncome = null

                                title = ""
                                source = ""
                                amount = ""

                                message = "Income Updated Successfully"
                            },

                            onError = {
                                message = it
                            }
                        )
                    }
                }
            ) {
                Text(
                    if (editingIncome == null)
                        "Add Income"
                    else
                        "Update Income"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (message.isNotEmpty()) {
                Text(message)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Total Income: ₹$totalIncome",
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (incomes.isEmpty()) {

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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "💵 No Income Added",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Add income records to track earnings."
                        )
                    }
                }
            }
        }
        items(incomes.reversed()) { income ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Text("Title: ${income.title}")

                    Text("Source: ${income.source}")

                    Text("Amount: ₹${income.amount}")

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {

                            if (income.documentId.isBlank()) {
                                return@Button
                            }

                            FirestoreSource.deleteIncome(
                                documentId = income.documentId,

                                onSuccess = {
                                    DashboardRefresh.triggerRefresh()
                                    incomes.remove(income)
                                },

                                onError = {
                                    println(it)
                                }
                            )
                        }
                    ) {
                        Text("Delete")
                    }
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {

                            editingIncome = income

                            title = income.title
                            source = income.source
                            amount = income.amount

                            message = "Editing Income..."
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