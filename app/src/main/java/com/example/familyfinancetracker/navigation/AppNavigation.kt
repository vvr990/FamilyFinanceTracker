package com.example.familyfinancetracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.familyfinancetracker.presentation.auth.LoginScreen
import com.example.familyfinancetracker.presentation.auth.RegisterScreen
import com.example.familyfinancetracker.presentation.home.HomeScreen

import com.example.familyfinancetracker.presentation.familymembers.FamilyMembersScreen
import com.example.familyfinancetracker.presentation.profile.ProfileScreen
import com.example.familyfinancetracker.presentation.recurring.RecurringExpensesScreen
import com.example.familyfinancetracker.presentation.payments.UpcomingPaymentsScreen
import com.example.familyfinancetracker.presentation.settings.SettingsScreen
import com.example.familyfinancetracker.presentation.familymembers.FamilyMembersScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {

            LoginScreen(
                onLoginClick = {
                    navController.navigate(Screen.Home.route)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }

        composable(Screen.Home.route) {

            HomeScreen(
                navController = navController
            )
        }

        composable(Screen.FamilyMembers.route) {
            FamilyMembersScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        composable(Screen.RecurringExpenses.route) {
            RecurringExpensesScreen()
        }

        composable(Screen.UpcomingPayments.route) {
            UpcomingPaymentsScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}