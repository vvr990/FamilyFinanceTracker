package com.example.familyfinancetracker.ui.components

import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DrawerItem(
    title: String,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(title)
        },
        selected = false,
        onClick = onClick
    )
}