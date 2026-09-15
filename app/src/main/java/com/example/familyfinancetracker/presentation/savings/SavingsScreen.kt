package com.example.familyfinancetracker.presentation.savings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.LaunchedEffect

import com.example.familyfinancetracker.data.model.Savings
import com.example.familyfinancetracker.data.remote.FirestoreSource

@Composable
fun SavingsScreen() {

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }

    var message by remember { mutableStateOf("") }

    var editingSavings by remember {
        mutableStateOf<Savings?>(null)
    }

    val savingsList = remember {
        mutableStateListOf<Savings>()
    }

    LaunchedEffect(Unit) {

        FirestoreSource.getSavings(
            onSuccess = { firestoreSavings ->

                savingsList.clear()
                savingsList.addAll(firestoreSavings)
            },
            onError = {
                println(it)
            }
        )
    }

    val totalSavings = savingsList.sumOf {
        it.amount.toIntOrNull() ?: 0
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                text = "Savings",
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

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = goal,
                onValueChange = {
                    goal = it
                },
                label = {
                    Text("Goal")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    if (
                        title.isBlank() ||
                        amount.isBlank() ||
                        goal.isBlank()
                    ) {
                        message = "Please fill all fields"
                        return@Button
                    }

                    if (editingSavings == null) {

                        val savings = Savings(
                            id = savingsList.size + 1,
                            title = title,
                            amount = amount,
                            goal = goal
                        )

                        FirestoreSource.addSavings(
                            savings = savings,

                            onSuccess = {

                                FirestoreSource.getSavings(

                                    onSuccess = { firestoreSavings ->

                                        savingsList.clear()
                                        savingsList.addAll(firestoreSavings)

                                        title = ""
                                        amount = ""
                                        goal = ""

                                        message =
                                            "Savings Added Successfully"
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
                    } else {

                        val updatedSavings =
                            editingSavings!!.copy(
                                title = title,
                                amount = amount,
                                goal = goal
                            )

                        FirestoreSource.updateSavings(
                            savings = updatedSavings,

                            onSuccess = {

                                val index =
                                    savingsList.indexOfFirst {
                                        it.documentId ==
                                                updatedSavings.documentId
                                    }

                                if (index != -1) {
                                    savingsList[index] =
                                        updatedSavings
                                }

                                editingSavings = null

                                title = ""
                                amount = ""
                                goal = ""

                                message =
                                    "Savings Updated Successfully"
                            },

                            onError = {
                                message = it
                            }
                        )
                    }
                }
            ) {

                Text(
                    if (editingSavings == null)
                        "Add Savings"
                    else
                        "Update Savings"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (message.isNotEmpty()) {
                Text(message)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Total Savings: ₹$totalSavings",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Number of Savings: ${savingsList.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(savingsList.reversed()) { savings ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Text("Title: ${savings.title}")

                    Text("Amount: ₹${savings.amount}")

                    Text("Goal: ${savings.goal}")

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {

                            if (
                                savings.documentId.isBlank()
                            ) {
                                return@Button
                            }

                            FirestoreSource.deleteSavings(
                                documentId =
                                    savings.documentId,

                                onSuccess = {
                                    savingsList.remove(
                                        savings
                                    )
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

                            editingSavings = savings

                            title = savings.title
                            amount = savings.amount
                            goal = savings.goal

                            message =
                                "Editing Savings..."
                        }
                    ) {
                        Text("Edit")
                    }
                }
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(100.dp)
            )
        }
    }
}