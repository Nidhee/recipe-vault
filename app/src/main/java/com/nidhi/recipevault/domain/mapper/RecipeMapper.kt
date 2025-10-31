package com.nidhi.recipevault.domain.mapper

import com.nidhi.recipevault.data.local.database.entity.IngredientEntity
import com.nidhi.recipevault.data.local.database.entity.MethodStepEntity
import com.nidhi.recipevault.data.local.database.entity.RecipeVaultEntity
import com.nidhi.recipevault.domain.model.Ingredient
import com.nidhi.recipevault.domain.model.MethodStep
import com.nidhi.recipevault.domain.model.Recipe
import javax.inject.Inject

class RecipeMapper @Inject constructor(private val enumMapper: EnumMapper) {

    /**
    Map from RecipeVaultEntity (data layer) to Recipe (domain model)
     */
    fun mapRecipeVaultEntityToDomain(
        entity: RecipeVaultEntity,
        ingredients: List<IngredientEntity>,
        methodSteps: List<MethodStepEntity>
    ): Recipe {
        return Recipe(
            recipeId = entity.recipeId,
            name = entity.name,
            description = entity.description,
            prepTimeMinutes = entity.prepTimeMinutes ?: 0,
            cookTimeMinutes = entity.cookTimeMinutes ?: 0,
            servings = entity.servings ?: 0,
            difficultyLevel = entity.difficultyLevel?.let { enumMapper.mapDifficultyLevelToDomain(it) },
            cuisine = entity.cuisine?.let { enumMapper.mapCuisineToDomain(it) },
            mealType = entity.mealType?.let { enumMapper.mapMealTypeToDomain(it) },
            thumbnail = entity.thumbnail ?: "",
            ingredients = ingredients.map { mapIngredientEntityToDomain(it) },
            methodSteps = methodSteps.map { mapMethodStepEntityToDomain(it) }
        )
    }
    /**
     *  Map from Recipe (domain model) to RecipeVaultEntity (data layer)
     */
    fun mapRecipeDomainToEntity(
        recipe: Recipe
    ): RecipeVaultEntity {
        return RecipeVaultEntity(
            recipeId = recipe.recipeId,
            name = recipe.name,
            description = recipe.description,
            prepTimeMinutes = recipe.prepTimeMinutes,
            cookTimeMinutes = recipe.cookTimeMinutes,
            servings = recipe.servings,
            difficultyLevel = recipe.difficultyLevel?.let { enumMapper.mapDifficultyLevelToData(it) },
            cuisine = recipe.cuisine?.let { enumMapper.mapCuisineToData(it) },
            mealType = recipe.mealType?.let { enumMapper.mapMealTypeToData(it) },
            thumbnail = recipe.thumbnail
        )
    }
    /**
     * Map IngredientEntity (data layer) to Ingredient (domain model)
     */
    private fun mapIngredientEntityToDomain(entity: IngredientEntity): Ingredient {
        return Ingredient(
            ingredientId = entity.ingredientId,
            recipeId = entity.recipeId,
            item = entity.item,
            qty = entity.qty ?: 0.0,
            maxQty = entity.maxQty ?: 0.0,
            unit = entity.unit?.let { enumMapper.mapRecipeUnitToDomain(it) }
        )
    }
    /**
     * Map Ingredient (domain model) to IngredientEntity (data layer)
     */
    fun mapIngredientDomainToEntity(
        recipeId: Int,
        ingredient: Ingredient
    ): IngredientEntity {
        return IngredientEntity(
            recipeId = recipeId,
            item = ingredient.item,
            qty = ingredient.qty,
            maxQty = ingredient.maxQty,
            unit = ingredient.unit?.let { enumMapper.mapRecipeUnitToData(ingredient.unit) }
        )
    }

    /**
     * Map MethodStepEntity (data layer) to MethodStep (domain model)
      */
    private fun mapMethodStepEntityToDomain(entity: MethodStepEntity): MethodStep {
        return MethodStep(
            stepId = entity.stepId,
            recipeId = entity.recipeId,
            stepOrder = entity.stepOrder,
            stepDescription = entity.stepDescription,
        )
    }

    /**
     * Map MethodStep (domain model) to MethodStepEntity (data layer)
     */
    fun mapMethodStepDomainToEntity(
        recipeId: Int,
        methodStep: MethodStep
    ): MethodStepEntity {
        return MethodStepEntity(
            recipeId = recipeId,
            stepOrder = methodStep.stepOrder,
            stepDescription = methodStep.stepDescription
        )
    }
}