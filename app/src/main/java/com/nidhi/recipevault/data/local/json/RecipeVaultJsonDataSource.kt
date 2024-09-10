package com.nidhi.recipevault.data.local.json

import android.content.Context

class RecipeVaultJsonDataSource {
    fun readJsonFromAssets(context: Context, fileName: String): String {
        val jsonString = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        println("recipe vault jsonString $jsonString")
        return jsonString
    }
}