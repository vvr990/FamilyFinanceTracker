package com.example.familyfinancetracker.data.remote


import com.example.familyfinancetracker.data.model.Savings
import com.example.familyfinancetracker.data.model.Income
import com.example.familyfinancetracker.data.model.Expense
import com.google.firebase.firestore.FirebaseFirestore
import com.example.familyfinancetracker.data.model.FamilyMember
import com.example.familyfinancetracker.data.model.RecurringExpense

object FirestoreSource {

    private val db = FirebaseFirestore.getInstance()


    fun addExpense(
        expense: Expense,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("expenses")
            .add(expense)
            .addOnSuccessListener { document ->

                document.update(
                    "documentId",
                    document.id
                )

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

    fun addIncome(
        income: Income,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("income")
            .add(income)
            .addOnSuccessListener { document ->

                document.update(
                    "documentId",
                    document.id
                )

                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun getIncome(
        onSuccess: (List<Income>) -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("income")
            .get()
            .addOnSuccessListener { result ->

                val incomeList = result.toObjects(
                    Income::class.java
                )

                onSuccess(incomeList)
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun updateIncome(
        income: Income,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("income")
            .document(income.documentId)
            .set(income)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun deleteIncome(
        documentId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("income")
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }


    fun addSavings(
        savings: Savings,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("savings")
            .add(savings)
            .addOnSuccessListener { document ->

                document.update(
                    "documentId",
                    document.id
                )

                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun getSavings(
        onSuccess: (List<Savings>) -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("savings")
            .get()
            .addOnSuccessListener { result ->

                val savingsList = result.documents.map { document ->

                    Savings(
                        documentId = document.id,
                        id = document.getLong("id")?.toInt() ?: 0,
                        title = document.getString("title") ?: "",
                        amount = document.getString("amount") ?: "",
                        goal = document.getString("goal") ?: ""
                    )
                }

                onSuccess(savingsList)
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun updateSavings(
        savings: Savings,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("savings")
            .document(savings.documentId)
            .set(savings)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun deleteSavings(
        documentId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("savings")
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }


    fun getTotalExpenses(
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("expenses")
            .get()
            .addOnSuccessListener { result ->

                val total = result.documents.sumOf {

                    it.getString("amount")
                        ?.toIntOrNull() ?: 0
                }

                onSuccess(total)
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun getTotalIncome(
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("income")
            .get()
            .addOnSuccessListener { result ->

                val total = result.documents.sumOf {

                    it.getString("amount")
                        ?.toIntOrNull() ?: 0
                }

                onSuccess(total)
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun getTotalSavings(
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("savings")
            .get()
            .addOnSuccessListener { result ->

                val total = result.documents.sumOf {

                    it.getString("amount")
                        ?.toIntOrNull() ?: 0
                }

                onSuccess(total)
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun addFamilyMember(
        familyMember: FamilyMember,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("familyMembers")
            .add(familyMember)
            .addOnSuccessListener { document ->

                document.update(
                    "documentId",
                    document.id
                )

                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun getFamilyMembers(
        onSuccess: (List<FamilyMember>) -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("familyMembers")
            .get()
            .addOnSuccessListener { result ->

                val familyMembers =
                    result.toObjects(
                        FamilyMember::class.java
                    )

                onSuccess(familyMembers)
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun updateFamilyMember(
        familyMember: FamilyMember,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("familyMembers")
            .document(familyMember.documentId)
            .set(familyMember)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun deleteFamilyMember(
        documentId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("familyMembers")
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }
    fun addRecurringExpense(
        recurringExpense: RecurringExpense,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("recurringExpenses")
            .add(recurringExpense)
            .addOnSuccessListener { document ->

                document.update(
                    "documentId",
                    document.id
                )

                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun getRecurringExpenses(
        onSuccess: (List<RecurringExpense>) -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("recurringExpenses")
            .get()
            .addOnSuccessListener { result ->

                val recurringExpenses =
                    result.toObjects(
                        RecurringExpense::class.java
                    )

                onSuccess(recurringExpenses)
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun updateRecurringExpense(
        recurringExpense: RecurringExpense,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("recurringExpenses")
            .document(recurringExpense.documentId)
            .set(recurringExpense)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun deleteRecurringExpense(
        documentId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("recurringExpenses")
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun getTotalRecurringExpenses(
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("recurringExpenses")
            .get()
            .addOnSuccessListener { result ->

                val total =
                    result.documents.sumOf {

                        it.getString("amount")
                            ?.toIntOrNull() ?: 0
                    }

                onSuccess(total)
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

}