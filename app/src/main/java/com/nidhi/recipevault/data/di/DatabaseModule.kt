package com.nidhi.recipevault.data.di

import android.content.Context
import androidx.room.Room
import com.nidhi.recipevault.data.local.database.RecipeVaultDatabase
import com.nidhi.recipevault.data.local.database.RoomDbInitializer
import com.nidhi.recipevault.data.local.database.dao.IngredientDao
import com.nidhi.recipevault.data.local.database.dao.MethodStepDao
import com.nidhi.recipevault.data.local.database.dao.RecipeVaultDao
import com.nidhi.recipevault.data.mapper.RecipeVaultModelToEntityMapper
import com.nidhi.recipevault.data.local.json.RecipeVaultJsonDataSource
import com.nidhi.recipevault.data.mapper.RecipeVaultJsonToModelMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun providesRecipeVaultDao(database: RecipeVaultDatabase): RecipeVaultDao {
        return database.recipeVaultDao()
    }

    @Provides
    fun providesIngredientDao(database: RecipeVaultDatabase) : IngredientDao {
        return database.ingredientDao()
    }

    @Provides
    fun providesMethodStepDao(database: RecipeVaultDatabase) : MethodStepDao {
        return database.methodStepDao()
    }

    @Provides
    @Singleton
    fun providesRecipeVaultDatabase(@ApplicationContext appContext: Context,
                                    recipeVaultDao: Provider<RecipeVaultDao>,
                                    methodStepDao: Provider<MethodStepDao>,
                                    ingredientDao: Provider<IngredientDao>,
                                    recipeVaultJsonDataSource: Provider<RecipeVaultJsonDataSource>,
                                    recipeVaultJsonToModelMapper: Provider<RecipeVaultJsonToModelMapper>,
                                    recipeVaultModelToEntityMapper: Provider<RecipeVaultModelToEntityMapper>,): RecipeVaultDatabase {
        return Room.databaseBuilder(
            appContext,
            RecipeVaultDatabase::class.java,
            "recipe_vault_database.db")
            .addCallback(
                RoomDbInitializer(recipeVaultDao,methodStepDao,ingredientDao,
                    recipeVaultJsonDataSource,
                    recipeVaultJsonToModelMapper,
                    recipeVaultModelToEntityMapper))
            .build()
    }
}