package com.nidhi.recipevault.com.nidhi.recipevault.domain.usecase

import com.nidhi.recipevault.com.nidhi.recipevault.domain.repository.RecipeRepository
import com.nidhi.recipevault.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRecipesUseCase @Inject constructor(private val recipeRepository: RecipeRepository) {
    operator fun invoke(): Flow<List<Recipe>> {
        return recipeRepository.getAllRecipes()
    }
}