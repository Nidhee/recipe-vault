package com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nidhi.recipevault.data.local.model.IngredientModel
import com.nidhi.recipevault.data.local.model.RecipeVaultModel

@Dao
interface RecipeVaultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeVault : RecipeVaultModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<IngredientModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMethodSteps(steps : List<String>)

    @Query("SELECT * FROM recipes where recipe_id = :recipeId")
    suspend fun getRecipeByID(recipeId : Int) : RecipeVaultModel?

    @Query("SELECT * FROM ingredients where recipe_id = :recipeId")
    suspend fun getIngredientsForRecipe(recipeId: Int) : List<IngredientModel>

    @Query("SELECT step_description FROM method_steps where recipe_id = :recipeId ORDER BY step_order ASC")
    suspend fun getMethodStepsForRecipe(recipeId: Int) : List<String>
}