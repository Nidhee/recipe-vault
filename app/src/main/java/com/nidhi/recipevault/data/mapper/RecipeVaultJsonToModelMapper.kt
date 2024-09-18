package com.nidhi.recipevault.data.mapper

import com.google.gson.Gson
import com.nidhi.recipevault.data.local.model.RecipeVaultModel

class RecipeVaultJsonToModelMapper {
    fun parseJsonToDomainModel(jsonString: String): RecipeVaultModel? {
        val gson = Gson()
        val recipeVault = gson.fromJson(jsonString, RecipeVaultModel::class.java)
        println("recipe vault object $recipeVault")
        return recipeVault
    }
}