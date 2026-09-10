package com.example.familyfinancetracker.data.remote

import com.example.familyfinancetracker.data.model.Expense
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreSource {

    private val db = FirebaseFirestore.getInstance()

    fun addExpense(
        expense: Expense,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("expenses")
            .add(expense)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun getExpenses(
        onSuccess: (List<Expense>) -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("expenses")
            .get()
            .addOnSuccessListener { result ->

                val expenses = result.documents.map { document ->

                    Expense(
                        documentId = document.id,
                        id = document.getLong("id")?.toInt() ?: 0,
                        title = document.getString("title") ?: "",
                        amount = document.getString("amount") ?: "",
                        category = document.getString("category") ?: ""
                    )
                }

                onSuccess(expenses)
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }
    fun deleteExpense(
        documentId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("expenses")
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }
    fun updateExpense(
        expense: Expense,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("expenses")
            .document(expense.documentId)
            .set(expense)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }
}