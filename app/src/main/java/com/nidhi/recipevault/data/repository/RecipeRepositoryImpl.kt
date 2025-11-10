package com.nidhi.recipevault.data.repository

import android.util.Log
import com.nidhi.recipevault.data.local.database.dao.IngredientDao
import com.nidhi.recipevault.data.local.database.dao.MethodStepDao
import com.nidhi.recipevault.data.local.database.dao.RecipeVaultDao
import com.nidhi.recipevault.data.local.database.entity.IngredientEntity
import com.nidhi.recipevault.data.local.database.entity.MethodStepEntity
import com.nidhi.recipevault.data.local.database.entity.RecipeVaultEntity
import com.nidhi.recipevault.data.mapper.RecipeVaultModelToEntityMapper
import com.nidhi.recipevault.com.nidhi.recipevault.data.mapper.RecipeMapper
import com.nidhi.recipevault.domain.repository.RecipeRepository
import com.nidhi.recipevault.utils.LogUtils
import com.nidhi.recipevault.domain.model.Recipe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeVaultDao: RecipeVaultDao,
    private val methodStepDao: MethodStepDao,
    private val ingredientDao: IngredientDao,
    private val recipeVaultModelToEntityMapper : RecipeVaultModelToEntityMapper,
    private val recipeMapper: RecipeMapper
) : RecipeRepository {

    override suspend fun insertRecipe(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override suspend fun insertRecipes(recipes: List<Recipe>) {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllRecipes(): Flow<List<Recipe>> {
        Log.d(LogUtils.getTag(this::class.java), "getAllRecipes")
        val recipeDBDataFlow: Flow<List<RecipeVaultEntity>> = recipeVaultDao.getAllRecipes()
        /* First flatten the Flow<List<RecipeVaultEntity>> so that we can process each entity in parallel, fetching the corresponding ingredients and method steps.*/
        return recipeDBDataFlow.flatMapLatest { recipeEntities : List<RecipeVaultEntity> ->
                if(recipeEntities.isEmpty()){
                    Log.e(LogUtils.getTag(this::class.java), "getAllRecipes >> fetching recipes is empty")
                    // Emit an empty list if no recipes are found
                    flow { emit(emptyList()) }
                } else {
                    val recipeFlows: List<Flow<Recipe>> =
                        recipeEntities.map { recipeEntity: RecipeVaultEntity ->
                            fetchRecipeWithDetails(recipeEntity)
                        }
                    /* combine collects each flow (Flow<Recipe>) in parallel and produces a single flow of a list (Flow<List<Recipe>>) */
                    combine(recipeFlows) { arrayOfResults: Array<Recipe> ->
                        arrayOfResults.toList()
                    }
                }
            }.catch { exception ->
                // Emit an empty list in case of an error
                Log.e(LogUtils.getTag(this::class.java), "getAllRecipes >> Error fetching recipes", exception)
                emit(emptyList())
            }
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