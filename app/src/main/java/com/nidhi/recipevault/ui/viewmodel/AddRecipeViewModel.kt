package com.nidhi.recipevault.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.CuisineDomain
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.DifficultyLevelDomain
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.Ingredient
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.MealTypeDomain
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.RecipeUnitDomain
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

    data class Step2Fields(
        val servings: String = "",
        val difficultyLevel: DifficultyLevelDomain? = null,
        val mealType: MealTypeDomain? = null,
        val cuisine: CuisineDomain? = null
    )

    data class Step2Errors(
        val servingsError: String? = null,
        val difficultyError: String? = null,
        val mealTypeError: String? = null,
        val cuisineError: String? = null
    )
    data class Step3Fields(
        val ingredients: MutableList<Ingredient> = mutableListOf()
    )

    private val _step3Fields = MutableStateFlow(Step3Fields())
    val step3Fields: StateFlow<Step3Fields> = _step3Fields

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _step1Fields = MutableStateFlow(Step1Fields())
    val step1Fields: StateFlow<Step1Fields> = _step1Fields

    private val _step1Errors = MutableStateFlow(Step1Errors())
    val step1Errors: StateFlow<Step1Errors> = _step1Errors

    private val _step2Fields = MutableStateFlow(Step2Fields())
    val step2Fields: StateFlow<Step2Fields> = _step2Fields

    private val _step2Errors = MutableStateFlow(Step2Errors())
    val step2Errors: StateFlow<Step2Errors> = _step2Errors

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

    // Step 2 Methods
    fun setServings(servings: String) {
        _step2Fields.update { it.copy(servings = servings) }
        if (servings.isNotBlank() && _step2Errors.value.servingsError != null) {
            _step2Errors.update { it.copy(servingsError = null) }
        }
    }

    fun setDifficultyLevel(difficulty: DifficultyLevelDomain?) {
        _step2Fields.update { it.copy(difficultyLevel = difficulty) }
        if (difficulty != null && _step2Errors.value.difficultyError != null) {
            _step2Errors.update { it.copy(difficultyError = null) }
        }
    }

    fun setMealType(mealType: MealTypeDomain?) {
        _step2Fields.update { it.copy(mealType = mealType) }
        if (mealType != null && _step2Errors.value.mealTypeError != null) {
            _step2Errors.update { it.copy(mealTypeError = null) }
        }
    }

    fun setCuisine(cuisine: CuisineDomain?) {
        _step2Fields.update { it.copy(cuisine = cuisine) }
        if (cuisine != null && _step2Errors.value.cuisineError != null) {
            _step2Errors.update { it.copy(cuisineError = null) }
        }
    }

    fun validateStep2(): Boolean {
        val fields = _step2Fields.value
        var valid = true
        var servingsErr: String? = null
        var difficultyErr: String? = null
        var mealTypeErr: String? = null
        var cuisineErr: String? = null

        if (fields.servings.isBlank()) {
            servingsErr = "Servings cannot be empty"
            valid = false
        } else {
            val servingsInt = fields.servings.toIntOrNull()
            if (servingsInt == null) {
                servingsErr = "Enter a valid number"
                valid = false
            } else if (servingsInt <= 0) {
                servingsErr = "Servings must be greater than 0"
                valid = false
            }
        }

        if (fields.difficultyLevel == null) {
            difficultyErr = "Please select a difficulty level"
            valid = false
        }

        if (fields.mealType == null) {
            mealTypeErr = "Please select a meal type"
            valid = false
        }

        if (fields.cuisine == null) {
            cuisineErr = "Please select a cuisine"
            valid = false
        }

        _step2Errors.update {
            it.copy(
                servingsError = servingsErr,
                difficultyError = difficultyErr,
                mealTypeError = mealTypeErr,
                cuisineError = cuisineErr
            )
        }
        return valid
    }
    fun addIngredient() {
        val newIngredient = Ingredient(
            recipeId = 0,
            item = "",
            qty = null,
            maxQty = null,
            unit = null
        )
        _step3Fields.update {
            val updatedList = it.ingredients.toMutableList()
            updatedList.add(0,newIngredient) //inserts at the top of list
            it.copy(ingredients = updatedList)
        }
    }
    fun removeIngredient(index: Int) {
        _step3Fields.update {
            val updatedList = it.ingredients.toMutableList()
            if (index in updatedList.indices) {
                updatedList.removeAt(index)
            }
            it.copy(ingredients = updatedList)
        }
    }

    fun updateIngredientItem(index: Int, item: String) {
        _step3Fields.update {
            val updatedList = it.ingredients.toMutableList()
            if (index in updatedList.indices) {
                updatedList[index] = updatedList[index].copy(item = item)
            }
            it.copy(ingredients = updatedList)
        }
    }
    fun updateIngredientQuantity(index: Int, qty: String) {
        _step3Fields.update {
            val updatedList = it.ingredients.toMutableList()
            if (index in updatedList.indices) {
                val qtyValue = qty.toDoubleOrNull()
                updatedList[index] = updatedList[index].copy(qty = qtyValue)
            }
            it.copy(ingredients = updatedList)
        }
    }

    fun updateIngredientUnit(index: Int, unit: RecipeUnitDomain) {
        _step3Fields.update {
            val updatedList = it.ingredients.toMutableList()
            if (index in updatedList.indices) {
                updatedList[index] = updatedList[index].copy(unit = unit)
            }
            it.copy(ingredients = updatedList)
        }
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
        _step2Fields.value = Step2Fields()
        _step2Errors.value = Step2Errors()
        _uiState.value = UiState()
    }
}


