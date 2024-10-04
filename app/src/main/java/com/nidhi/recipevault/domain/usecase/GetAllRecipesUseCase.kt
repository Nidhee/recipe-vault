package com.nidhi.recipevault.com.nidhi.recipevault.domain.usecase

import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.RecipeVaultEntity
import com.nidhi.recipevault.com.nidhi.recipevault.domain.repository.RecipeRepository
import com.nidhi.recipevault.data.local.model.RecipeVaultModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRecipesUseCase @Inject constructor(private val recipeRepository: RecipeRepository) {
    operator fun invoke(): Flow<List<RecipeVaultEntity>> {
        return recipeRepository.getAllRecipes()
    }
}