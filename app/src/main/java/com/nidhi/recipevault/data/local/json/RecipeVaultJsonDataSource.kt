package com.nidhi.recipevault.data.local.json

import android.content.Context
import android.util.Log
import com.nidhi.recipevault.com.nidhi.recipevault.utils.LogUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RecipeVaultJsonDataSource @Inject constructor(@ApplicationContext private val context: Context){
    suspend fun readJsonFromAssets(fileName: String): String {
        val jsonString = context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        Log.d(LogUtils.getTag(this::class.java), "recipe vault jsonString $jsonString")
        return jsonString
    }
}