package com.example.familyfinancetracker.presentation.expenses

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.familyfinancetracker.data.model.Expense

class ExpenseViewModel : ViewModel() {

    val expenses = mutableStateListOf<Expense>()

    fun addExpense(
        title: String,
        amount: String,
        category: String
    ) {
        expenses.add(
            Expense(
                id = expenses.size + 1,
                title = title,
                amount = amount,
                category = category
            )
        )
    }

    fun deleteExpense(expense: Expense) {
        expenses.remove(expense)
    }
}