package com.nidhi.recipevault.data.di

import com.nidhi.recipevault.data.local.database.dao.IngredientDao
import com.nidhi.recipevault.data.local.database.dao.MethodStepDao
import com.nidhi.recipevault.data.local.database.dao.RecipeVaultDao
import com.nidhi.recipevault.data.mapper.RecipeVaultModelToEntityMapper
import com.nidhi.recipevault.data.repository.RecipeRepositoryImpl
import com.nidhi.recipevault.domain.mapper.RecipeMapper
import com.nidhi.recipevault.domain.repository.RecipeRepository
import com.nidhi.recipevault.data.local.json.RecipeVaultJsonDataSource
import com.nidhi.recipevault.data.mapper.RecipeVaultJsonToModelMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeVaultDao: RecipeVaultDao,
        methodStepDao: MethodStepDao,
        ingredientDao: IngredientDao,
        recipeVaultModelToEntityMapper: RecipeVaultModelToEntityMapper,
        recipeMapper: RecipeMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            recipeVaultDao,
            methodStepDao,
            ingredientDao,
            recipeVaultModelToEntityMapper,
            recipeMapper
        )
    }
}