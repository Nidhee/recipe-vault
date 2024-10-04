package com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.IngredientEntity
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.MethodStepEntity
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.RecipeVaultEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeVaultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeVault : RecipeVaultEntity)

    @Query("SELECT * FROM recipes where recipe_id = :recipeId")
    suspend fun getRecipeByID(recipeId : Int) : RecipeVaultEntity?
    @Query("SELECT * FROM recipes")
    fun getAllRecipes() : Flow<List<RecipeVaultEntity>>
}