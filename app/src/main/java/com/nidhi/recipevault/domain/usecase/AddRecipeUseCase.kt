package com.nidhi.recipevault.com.nidhi.recipevault.domain.usecase

import com.nidhi.recipevault.domain.repository.RecipeRepository
import com.nidhi.recipevault.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddRecipeUseCase @Inject constructor(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke(recipe: Recipe) : Recipe{
        // Generate next recipe ID
        val nextId = recipeRepository.getNextRecipeId()

        // Create recipe with generated ID
        val recipeWithId = recipe.copy(recipeId = nextId)

        // Insert recipe with all related data
        recipeRepository.insertRecipe(recipeWithId)

        return recipeWithId
    }
}