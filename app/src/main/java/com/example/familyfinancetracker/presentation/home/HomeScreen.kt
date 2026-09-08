package com.example.familyfinancetracker.presentation.home

import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

import com.example.familyfinancetracker.presentation.expenses.ExpensesScreen
import com.example.familyfinancetracker.presentation.income.IncomeScreen
import com.example.familyfinancetracker.presentation.savings.SavingsScreen
import com.example.familyfinancetracker.ui.components.DrawerContent
import com.example.familyfinancetracker.ui.components.SummaryCard

import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController) {

    var selectedTab by remember {
        mutableStateOf(0)
    }

    val tabs = listOf(
        "Expenses",
        "Income",
        "Savings"
    )

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    navController = navController
                )
            }
        }
    ) {

        Scaffold(

            topBar = {

                TopAppBar(

                    title = {
                        Text("Family Finance Tracker")
                    },

                    navigationIcon = {

                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            },

            floatingActionButton = {

                FloatingActionButton(
                    onClick = { }
                ) {
                    Text("+")
                }
            }

        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                Text(
                    text = "September 2026",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    SummaryCard(
                        title = "Income",
                        amount = "₹95,000"
                    )

                    SummaryCard(
                        title = "Expenses",
                        amount = "₹52,000"
                    )

                    SummaryCard(
                        title = "Savings",
                        amount = "₹43,000"
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                TabRow(
                    selectedTabIndex = selectedTab
                ) {

                    tabs.forEachIndexed { index, title ->

                        Tab(
                            selected = selectedTab == index,
                            onClick = {
                                selectedTab = index
                            },
                            text = {
                                Text(title)
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    when (selectedTab) {

                        0 -> ExpensesScreen()

                        1 -> IncomeScreen()

                        2 -> SavingsScreen()
                    }
                }
            }
        }
    }
}