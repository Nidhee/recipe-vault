package com.nidhi.recipevault.com.nidhi.recipevault.data.di

import android.content.Context
import com.nidhi.recipevault.data.local.json.RecipeVaultJsonDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object JSONDataSourceModule {

    @Provides
    fun providesRecipeVaultJSONDataSource(@ApplicationContext appContext: Context): RecipeVaultJsonDataSource {
        return RecipeVaultJsonDataSource(context = appContext)
    }
}