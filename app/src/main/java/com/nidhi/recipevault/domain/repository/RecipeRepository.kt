package com.nidhi.recipevault.com.nidhi.recipevault.domain.repository

import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.RecipeVaultEntity
import com.nidhi.recipevault.data.local.model.RecipeVaultModel
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun insertRecipesToDB(recipes: RecipeVaultModel)
    fun getAllRecipes(): Flow<List<RecipeVaultEntity>>
}