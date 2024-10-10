package com.nidhi.recipevault.com.nidhi.recipevault.data.local.database

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.JsonSyntaxException
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao.IngredientDao
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao.MethodStepDao
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao.RecipeVaultDao
import com.nidhi.recipevault.com.nidhi.recipevault.data.mapper.RecipeVaultModelToEntityMapper
import com.nidhi.recipevault.com.nidhi.recipevault.utils.LogUtils
import com.nidhi.recipevault.data.local.json.RecipeVaultJsonDataSource
import com.nidhi.recipevault.data.mapper.RecipeVaultJsonToModelMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

class RoomDbInitializer (
    private val recipeVaultDao: Provider<RecipeVaultDao>,
    private val methodStepDao:  Provider<MethodStepDao>,
    private val ingredientDao:  Provider<IngredientDao>,
    private val recipeVaultJsonDataSource: Provider<RecipeVaultJsonDataSource>,
    private val recipeVaultJsonToModelMapper: Provider<RecipeVaultJsonToModelMapper>,
    private val recipeVaultModelToEntityMapper: Provider<RecipeVaultModelToEntityMapper>,
): RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(LogUtils.getTag(this::class.java), "room database callback >> RoomDbInitializer")
                // Read the JSON from RecipeVaultJsonDataSource
                val recipes = recipeVaultJsonDataSource.get().readJsonFromAssets("RecipeVaultJSON.json")
                // parse json string to model
                val recipeVaultModel = recipeVaultJsonToModelMapper.get().parseJsonToDomainModel(recipes)

                if (recipeVaultModel.isNullOrEmpty()) {
                    Log.e(LogUtils.getTag(this::class.java), "No valid recipes found in JSON")
                }
                // Insert data models into Room database
                recipeVaultModel?.forEach { recipe ->
                    recipeVaultDao.get().insertRecipe(
                        recipeVaultModelToEntityMapper.get()
                            .mapRecipeVaultItemModelToEntity(recipeModel = recipe)
                    )
                    ingredientDao.get().insertIngredients(
                        recipeVaultModelToEntityMapper.get()
                            .mapIngredientModelToEntity(recipeModel = recipe)
                    )
                    methodStepDao.get().insertMethodSteps(
                        recipeVaultModelToEntityMapper.get()
                            .mapMethodStepModelToEntity(recipeModel = recipe)
                    )
                }
            }catch (e: JsonSyntaxException) {
                Log.e(LogUtils.getTag(this::class.java), "room database callback >> RoomDbInitializer >> JSON parsing error\n$e")
                // Update the error state
                DatabaseErrorStateHolder.updateErrorState(e)
            } catch (e: Exception) {
                Log.e(LogUtils.getTag(this::class.java), "room database callback >> RoomDbInitializer >> Unknown error occurred\n$e")
                // Update the error state
                DatabaseErrorStateHolder.updateErrorState(e)
            }
        }
    }
}