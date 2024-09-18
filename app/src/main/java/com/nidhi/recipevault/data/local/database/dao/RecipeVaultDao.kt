package com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.IngredientEntity
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.MethodStepEntity
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.RecipeVaultEntity


@Dao
interface RecipeVaultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeVault : RecipeVaultEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<IngredientEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMethodSteps(steps : List<MethodStepEntity>)

    @Query("SELECT * FROM recipes where recipe_id = :recipeId")
    suspend fun getRecipeByID(recipeId : Int) : RecipeVaultEntity?

    @Query("SELECT * FROM ingredients where recipe_id = :recipeId")
    suspend fun getIngredientsForRecipe(recipeId: Int) : List<IngredientEntity>

    @Query("SELECT * FROM method_steps where recipe_id = :recipeId ORDER BY step_order ASC")
    suspend fun getMethodStepsForRecipe(recipeId: Int) : List<MethodStepEntity>
}