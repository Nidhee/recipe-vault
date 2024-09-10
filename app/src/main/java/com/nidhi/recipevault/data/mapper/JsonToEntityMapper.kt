package com.nidhi.recipevault.data.mapper

import com.google.gson.Gson
import com.nidhi.recipevault.data.local.model.RecipeVault

class JsonToEntityMapper {
    fun parseJsonToEntityModel(jsonString: String): RecipeVault? {
        val gson = Gson()
        val recipeVault = gson.fromJson(jsonString, RecipeVault::class.java)
        println("recipe vault object $recipeVault")
        return recipeVault
    }
}