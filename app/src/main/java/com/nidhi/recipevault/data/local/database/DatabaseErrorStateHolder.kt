package com.nidhi.recipevault.com.nidhi.recipevault.data.local.database

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Holds error state occurred during database creation
object DatabaseErrorStateHolder {
    private val _errorState = MutableStateFlow<Throwable?>(null)
    val errorState: StateFlow<Throwable?> = _errorState

    // Function to update error state
    fun updateErrorState(error: Throwable?) {
        _errorState.value = error
    }
}