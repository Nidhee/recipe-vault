package com.nidhi.recipevault.com.nidhi.recipevault.data.di

import android.content.Context
import androidx.room.Room
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.RecipeVaultDatabase
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao.RecipeVaultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun providesRecipeVaultDao(database: RecipeVaultDatabase): RecipeVaultDao {
        return database.recipeVaultDao()
    }

    @Provides
    fun providesRecipeVaultDatabase(@ApplicationContext appContext: Context): RecipeVaultDatabase {
        return Room.databaseBuilder(
            appContext,
            RecipeVaultDatabase::class.java,
            "recipevault.db"
        ).build()
    }
}