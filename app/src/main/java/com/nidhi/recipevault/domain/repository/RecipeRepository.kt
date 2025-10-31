package com.nidhi.recipevault.domain.repository

import com.nidhi.recipevault.data.local.database.entity.RecipeVaultEntity
import com.nidhi.recipevault.data.local.model.RecipeVaultModel
import com.nidhi.recipevault.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun insertRecipesToDB(recipes: RecipeVaultModel)
    fun getAllRecipes(): Flow<List<Recipe>>
}