package com.example.familyfinancetracker.presentation.income

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.familyfinancetracker.data.model.Income

@Composable
fun IncomeScreen() {

    var source by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val incomes = remember {
        mutableStateListOf<Income>()
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
                        source.isBlank() ||
                        amount.isBlank()
                    ) return@Button

                    incomes.add(
                        Income(
                            id = incomes.size + 1,
                            source = source,
                            amount = amount
                        )
                    )

                    source = ""
                    amount = ""
                }
            ) {
                Text("Add Income")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Total Income: ₹$totalIncome",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))
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

                    Text("Source: ${income.source}")

                    Text("Amount: ₹${income.amount}")

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {
                            incomes.remove(income)
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