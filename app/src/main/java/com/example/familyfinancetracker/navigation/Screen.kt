package com.example.familyfinancetracker.navigation

sealed class Screen(val route: String) {

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Home : Screen("home")

    data object Profile : Screen("profile")

    data object FamilyMembers : Screen("family_members")

    data object RecurringExpenses : Screen("recurring_expenses")

    data object UpcomingPayments : Screen("upcoming_payments")

    data object Settings : Screen("settings")
}