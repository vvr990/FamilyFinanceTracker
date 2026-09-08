package com.example.familyfinancetracker.data.model

data class FamilyMember(
    val id: Int,
    var name: String,
    var age: String,
    var relation: String,
    var contribution: String
)