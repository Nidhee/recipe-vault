package com.nidhi.recipevault.com.nidhi.recipevault.data.repository

import android.util.Log
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao.IngredientDao
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao.MethodStepDao
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao.RecipeVaultDao
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.RecipeVaultEntity
import com.nidhi.recipevault.com.nidhi.recipevault.data.mapper.RecipeVaultModelToEntityMapper
import com.nidhi.recipevault.com.nidhi.recipevault.domain.repository.RecipeRepository
import com.nidhi.recipevault.com.nidhi.recipevault.utils.LogUtils
import com.nidhi.recipevault.data.local.json.RecipeVaultJsonDataSource
import com.nidhi.recipevault.data.local.model.RecipeVaultModel
import com.nidhi.recipevault.data.mapper.RecipeVaultJsonToModelMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeVaultDao: RecipeVaultDao,
    private val methodStepDao: MethodStepDao,
    private val ingredientDao: IngredientDao,
    private val recipeVaultModelToEntityMapper : RecipeVaultModelToEntityMapper
) : RecipeRepository {
    override suspend fun insertRecipesToDB(recipes: RecipeVaultModel) {
        Log.d(LogUtils.getTag(this::class.java), "insertRecipesToDB")
        recipes.forEach { recipe ->
            recipeVaultDao.insertRecipe(
                recipeVaultModelToEntityMapper.mapRecipeVaultItemModelToEntity(recipeModel = recipe)
            )
            ingredientDao.insertIngredients(
                recipeVaultModelToEntityMapper.mapIngredientModelToEntity(recipeModel = recipe)
            )
            methodStepDao.insertMethodSteps(
                recipeVaultModelToEntityMapper.mapMethodStepModelToEntity(recipeModel = recipe)
            )
        }
    }

    override fun getAllRecipes(): Flow<List<RecipeVaultEntity>> {
        return recipeVaultDao.getAllRecipes()
    }
}