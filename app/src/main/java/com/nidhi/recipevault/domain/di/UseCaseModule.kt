package com.nidhi.recipevault.com.nidhi.recipevault.domain.di

import com.nidhi.recipevault.com.nidhi.recipevault.domain.repository.RecipeRepository
import com.nidhi.recipevault.com.nidhi.recipevault.domain.usecase.GetAllRecipesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun providesGetAllRecipesUseCase(recipeRepository: RecipeRepository) : GetAllRecipesUseCase {
        return GetAllRecipesUseCase(recipeRepository)
    }
}