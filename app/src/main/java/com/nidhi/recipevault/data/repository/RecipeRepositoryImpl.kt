package com.nidhi.recipevault.com.nidhi.recipevault.data.repository

import android.util.Log
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao.IngredientDao
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao.MethodStepDao
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao.RecipeVaultDao
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.IngredientEntity
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.MethodStepEntity
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.RecipeVaultEntity
import com.nidhi.recipevault.com.nidhi.recipevault.data.mapper.RecipeVaultModelToEntityMapper
import com.nidhi.recipevault.com.nidhi.recipevault.domain.mapper.RecipeMapper
import com.nidhi.recipevault.com.nidhi.recipevault.domain.repository.RecipeRepository
import com.nidhi.recipevault.com.nidhi.recipevault.utils.LogUtils
import com.nidhi.recipevault.data.local.model.RecipeVaultModel
import com.nidhi.recipevault.domain.model.Recipe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeVaultDao: RecipeVaultDao,
    private val methodStepDao: MethodStepDao,
    private val ingredientDao: IngredientDao,
    private val recipeVaultModelToEntityMapper : RecipeVaultModelToEntityMapper,
    private val recipeMapper: RecipeMapper
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
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllRecipes(): Flow<List<Recipe>> {
        val recipeDBDataFlow: Flow<List<RecipeVaultEntity>> = recipeVaultDao.getAllRecipes()
        val recipeDomainFlow: Flow<List<Recipe>> =
            /* First flatten the Flow<List<RecipeVaultEntity>> so that we can process each entity in parallel,
                fetching the corresponding ingredients and method steps.
             */
            recipeDBDataFlow.flatMapLatest { recipeEntities : List<RecipeVaultEntity> ->
                val recipeFlows: List<Flow<Recipe>> = recipeEntities.map { recipeEntity : RecipeVaultEntity ->
                    fetchRecipeWithDetails(recipeEntity)
                }
                /* combine collects each flow (Flow<Recipe>) in parallel and produces a single flow of a list (Flow<List<Recipe>>) */
                combine(recipeFlows) { arrayOfResults: Array<Recipe> ->
                    arrayOfResults.toList()
                }
            }
        return recipeDomainFlow
    }

    /**
     * For each RecipeVaultEntity, fetch the associated ingredients and method steps
     * This helper function returns a Flow<Recipe> by combining two flows—ingredientsFlow and methodStepsFlow.
     * It maps the RecipeVaultEntity data to a Recipe domain model using the RecipeMapper.
     */
    private fun fetchRecipeWithDetails(recipeEntity: RecipeVaultEntity): Flow<Recipe> {
        val ingredientsFlow: Flow<List<IngredientEntity>> = ingredientDao.getIngredientsForRecipe(recipeEntity.recipeId)
        val methodStepsFlow: Flow<List<MethodStepEntity>> = methodStepDao.getMethodStepsForRecipe(recipeEntity.recipeId)

        val recipe: Flow<Recipe> =
            combine(ingredientsFlow, methodStepsFlow) { ingredientsEntity : List<IngredientEntity>, methodStepsEntity : List<MethodStepEntity> ->
                recipeMapper.mapRecipeVaultEntityToDomain(
                    recipeEntity,
                    ingredientsEntity,
                    methodStepsEntity
                )
            }
        return recipe
    }
}