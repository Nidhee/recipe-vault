package com.nidhi.recipevault.ui.state

import com.nidhi.recipevault.domain.model.Recipe

sealed class RecipeUiState {
    /**
     * A "data object" is identical to a regular "object" but provides a default implementation of the toString() function that will print its name
     */
    data object Loading : RecipeUiState()
    data class Success(val recipes: List<Recipe>) : RecipeUiState()
    data class Error(val message: String) : RecipeUiState()
    data object NoRecipes : RecipeUiState()
}