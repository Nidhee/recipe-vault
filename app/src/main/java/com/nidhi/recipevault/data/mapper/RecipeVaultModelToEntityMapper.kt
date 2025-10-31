package com.nidhi.recipevault.data.mapper

import com.nidhi.recipevault.data.local.database.Cuisine
import com.nidhi.recipevault.data.local.database.DifficultyLevel
import com.nidhi.recipevault.data.local.database.MealType
import com.nidhi.recipevault.data.local.database.RecipeUnit
import com.nidhi.recipevault.data.local.database.entity.IngredientEntity
import com.nidhi.recipevault.data.local.database.entity.MethodStepEntity
import com.nidhi.recipevault.data.local.database.entity.RecipeVaultEntity
import com.nidhi.recipevault.data.local.model.MethodStepModel
import com.nidhi.recipevault.data.local.model.RecipeVaultItemModel
import javax.inject.Inject

/**
 *  Handles domain models to entity and entity to domain models mapping
 */
class RecipeVaultModelToEntityMapper @Inject constructor(){

    /**
     * TODO : check this mapper is needed or not
     *  Maps method step model to method step entity
     */
    fun mapToMethodStepEntity(methodStepModel: MethodStepModel): MethodStepEntity {
        return MethodStepEntity(
            recipeId = methodStepModel.recipeId,
            stepOrder = methodStepModel.stepOrder,
            stepDescription = methodStepModel.stepDescription
        )
    }
    /**
     * TODO : check this mapper is needed or not
     *  Maps method step entity to method step model
     */
    fun mapToMethodStepModel(methodStepEntity: MethodStepEntity): MethodStepModel {
        return MethodStepModel(
            recipeId = methodStepEntity.recipeId,
            stepOrder = methodStepEntity.stepOrder,
            stepDescription = methodStepEntity.stepDescription
        )
    }

    /**
     Mapping from domain model (RecipeVaultItemModel) to entity (RecipeEntity)
     */
    fun mapRecipeVaultItemModelToEntity(recipeModel: RecipeVaultItemModel): RecipeVaultEntity {
        return RecipeVaultEntity(
            recipeId = recipeModel.recipeId,
            name = recipeModel.name,
            description = recipeModel.description,
            prepTimeMinutes = recipeModel.prepTimeMinutes,
            cookTimeMinutes = recipeModel.cookTimeMinutes,
            servings = recipeModel.servings,
            difficultyLevel = stringToDifficultyLevel(recipeModel.difficultyLevel),
            mealType = stringToMealType(recipeModel.mealType),
            cuisine = stringToCuisine(recipeModel.cuisine),
            thumbnail = recipeModel.thumbnail
        )
    }

    /**
     * Map from Method (List<String>) to MethodStepEntity
     */
    fun mapMethodStepModelToEntity(recipeModel: RecipeVaultItemModel): List<MethodStepEntity> {
        return recipeModel.method.mapIndexed { index, step ->
            MethodStepEntity(
                recipeId = recipeModel.recipeId,
                stepOrder = index + 1,
                stepDescription = step
            )
        }
    }
    /**
     * Map from Ingredient (List<String>)  to IngredientEntity
      */
    fun mapIngredientModelToEntity(recipeModel: RecipeVaultItemModel): List<IngredientEntity> {
        return recipeModel.ingredients.map { ingredientModel ->
            IngredientEntity(
                recipeId = recipeModel.recipeId,
                item = ingredientModel.item,
                qty = ingredientModel.qty,
                maxQty = ingredientModel.maxQty,  // Assuming you have a `maxQuantity` in your model
                unit = stringToRecipeUnit(ingredientModel.unit)  // Assuming the unit is handled as an enum
            )
        }
    }
    /**
     * Method to convert String to DifficultyLevel enum
      */
    private fun stringToDifficultyLevel(value: String): DifficultyLevel {
        return try {
            DifficultyLevel.valueOf(value.uppercase()) // Handle case-sensitivity
        } catch (e: IllegalArgumentException) {
            DifficultyLevel.EASY // Fallback to a default value
        }
    }

    /**
     * Method to convert String to MealType enum
     */
    private fun stringToMealType(value: String): MealType {
        return try {
            MealType.valueOf(value.uppercase())
        } catch (e: IllegalArgumentException) {
            MealType.DRINK // Fallback to a default value
        }
    }

    /**
     *   Method to convert String to Cuisine enum
      */
    private fun stringToCuisine(value: String): Cuisine {
        return try {
            Cuisine.valueOf(value.uppercase())
        } catch (e: IllegalArgumentException) {
            Cuisine.INDIAN // Fallback to a default value
        }
    }

    /**
     *   Method to convert String to recipe unit enum
     */
    private fun stringToRecipeUnit(value: String): RecipeUnit {
        return try {
            RecipeUnit.valueOf(value.uppercase())
        } catch (e: IllegalArgumentException) {
            RecipeUnit.NOS // Fallback to a default value
        }
    }
}