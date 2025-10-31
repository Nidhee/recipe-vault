package com.nidhi.recipevault.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nidhi.recipevault.data.local.database.dao.IngredientDao
import com.nidhi.recipevault.data.local.database.dao.MethodStepDao
import com.nidhi.recipevault.data.local.database.dao.RecipeVaultDao
import com.nidhi.recipevault.data.local.database.entity.IngredientEntity
import com.nidhi.recipevault.data.local.database.entity.MethodStepEntity
import com.nidhi.recipevault.data.local.database.entity.RecipeVaultEntity


/**
 * SQLite Database for storing the recipe database.
 */
@Database(
    entities = [RecipeVaultEntity::class, IngredientEntity::class, MethodStepEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeVaultDatabase : RoomDatabase() {
    abstract fun recipeVaultDao(): RecipeVaultDao
    abstract fun ingredientDao() : IngredientDao
    abstract fun methodStepDao() : MethodStepDao
}