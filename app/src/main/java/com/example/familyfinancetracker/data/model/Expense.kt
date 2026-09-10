package com.example.familyfinancetracker.data.model

data class Expense(
    val documentId: String = "",
    val id: Int = 0,
    val title: String = "",
    val amount: String = "",
    val category: String = ""
)