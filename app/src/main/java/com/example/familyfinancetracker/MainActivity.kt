package com.example.familyfinancetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.familyfinancetracker.navigation.AppNavigation
import com.example.familyfinancetracker.ui.theme.FamilyFinanceTrackerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            FamilyFinanceTrackerTheme {
                AppNavigation()
            }
        }
    }
}