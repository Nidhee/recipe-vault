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

    data class Step1Fields(
        val name: String = "",
        val description: String = "",
        val cookTimeMinutes: String = "",
        val prepTimeMinutes: String = ""
    )

    data class Step1Errors(
        val nameError: String? = null,
        val descriptionError: String? = null,
        val cookTimeError: String? = null,
        val prepTimeError: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _step1Fields = MutableStateFlow(Step1Fields())
    val step1Fields: StateFlow<Step1Fields> = _step1Fields

    private val _step1Errors = MutableStateFlow(Step1Errors())
    val step1Errors: StateFlow<Step1Errors> = _step1Errors

    fun setName(name: String) {
        _step1Fields.update { it.copy(name = name) }
        // Clear error as user types
        if (name.isNotBlank() && _step1Errors.value.nameError != null) {
            _step1Errors.update { it.copy(nameError = null) }
        }
    }
    fun setDescription(description: String) {
        _step1Fields.update { it.copy(description = description) }
        // Clear error as user types
        if (description.isNotBlank() && _step1Errors.value.descriptionError != null) {
            _step1Errors.update { it.copy(descriptionError = null) }
        }
    }
    fun setCookTimeMinutes(cookTime: String) {
        _step1Fields.update { it.copy(cookTimeMinutes = cookTime) }
        if (cookTime.isNotBlank() && _step1Errors.value.cookTimeError != null) {
            _step1Errors.update { it.copy(cookTimeError = null) }
        }
    }

    fun setPrepTimeMinutes(prepTime: String) {
        _step1Fields.update { it.copy(prepTimeMinutes = prepTime) }
        if (prepTime.isNotBlank() && _step1Errors.value.prepTimeError != null) {
            _step1Errors.update { it.copy(prepTimeError = null) }
        }
    }


    fun validateStep1(): Boolean {
        val fields = _step1Fields.value
        var valid = true
        var nameErr: String? = null
        var descErr: String? = null
        var cookErr: String? = null
        var prepErr: String? = null

        if (fields.name.isBlank()) {
            nameErr = "Name cannot be empty"
            valid = false
        }
        // Note: Description is optional, so we don't add validation for it
        if (fields.cookTimeMinutes.isBlank()) {
            cookErr = "Cook time required"
            valid = false
        } else if (fields.cookTimeMinutes.toIntOrNull() == null) {
            cookErr = "Enter a valid number"
            valid = false
        }
        if (fields.prepTimeMinutes.isBlank()) {
            prepErr = "Prep time required"
            valid = false
        } else if (fields.prepTimeMinutes.toIntOrNull() == null) {
            prepErr = "Enter a valid number"
            valid = false
        }

        _step1Errors.update { it.copy(
            nameError = nameErr,
            descriptionError = descErr,
            cookTimeError = cookErr,
            prepTimeError = prepErr
        ) }
        return valid
    }

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

    fun resetAll() {
        _step1Fields.value = Step1Fields()
        _step1Errors.value = Step1Errors()
        _uiState.value = UiState()
    }
}


