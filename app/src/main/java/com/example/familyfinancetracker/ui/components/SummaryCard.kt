package com.example.familyfinancetracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SummaryCard(
    title: String,
    amount: String
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(title)
            Text(
                text = amount,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}