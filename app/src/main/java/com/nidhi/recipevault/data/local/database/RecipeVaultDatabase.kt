package com.nidhi.recipevault.com.nidhi.recipevault.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.dao.RecipeVaultDao

abstract class RecipeVaultDatabase : RoomDatabase() {
    abstract fun recipeVaultDao() : RecipeVaultDao
}