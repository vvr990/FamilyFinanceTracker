package com.example.familyfinancetracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import com.example.familyfinancetracker.navigation.Screen


@Composable
fun DrawerContent(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Family Finance Tracker",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        DrawerItem("Profile") {
            navController.navigate(Screen.Profile.route)
        }

        DrawerItem("Family Members") {
            navController.navigate(Screen.FamilyMembers.route)
        }

        DrawerItem("Recurring Expenses") {
            navController.navigate(Screen.RecurringExpenses.route)
        }

        DrawerItem("Upcoming Payments") {
            navController.navigate(Screen.UpcomingPayments.route)
        }

        DrawerItem("Settings") {
            navController.navigate(Screen.Settings.route)
        }

        DrawerItem("Logout") {
            navController.navigate(Screen.Login.route)
        }
    }
}