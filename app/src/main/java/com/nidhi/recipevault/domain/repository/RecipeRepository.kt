package com.nidhi.recipevault.domain.repository

import com.nidhi.recipevault.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun insertRecipe(recipe: Recipe)
    suspend fun insertRecipes(recipes: List<Recipe>)
    fun getAllRecipes(): Flow<List<Recipe>>
    suspend fun getNextRecipeId(): Int
}