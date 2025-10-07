package com.nidhi.recipevault.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    // Inject repositories
) : ViewModel() {

    data class UiState(
        val step: Int = 0,
        val isSubmitting: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun goToNextStep() {
        _uiState.update { it.copy(step = it.step + 1) }
    }

    fun goToPreviousStep() {
        _uiState.update { current ->
            val previous = if (current.step > 0) current.step - 1 else 0
            current.copy(step = previous)
        }
    }

    fun submit(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }
            try {
                // TODO: Persist to database via repository
                onSuccess()
            } catch (t: Throwable) {
                onError(t)
            } finally {
                _uiState.update { it.copy(isSubmitting = false) }
            }
        }
    }
}


