package com.example.familyfinancetracker.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object DashboardRefresh {

    private val _refresh = MutableStateFlow(false)

    val refresh: StateFlow<Boolean> = _refresh

    fun triggerRefresh() {
        _refresh.value = !_refresh.value
    }
}