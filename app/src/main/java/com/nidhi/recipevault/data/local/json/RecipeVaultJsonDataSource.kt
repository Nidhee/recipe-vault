package com.nidhi.recipevault.data.local.json

import android.content.Context
import javax.inject.Inject

class RecipeVaultJsonDataSource @Inject constructor(private val context: Context){
    suspend fun readJsonFromAssets(fileName: String): String {
        val jsonString = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        println("recipe vault jsonString $jsonString")
        return jsonString
    }
}