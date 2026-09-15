package com.example.familyfinancetracker.data.model

data class FamilyMember(
    val documentId: String = "",
    val id: Int = 0,
    var name: String = "",
    var age: String = "",
    var relation: String = "",
    var contribution: String = ""
)