package com.nidhi.recipevault.com.nidhi.recipevault.ui.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nidhi.recipevault.com.nidhi.recipevault.ui.viewmodel.RecipeViewModel
import com.nidhi.recipevault.com.nidhi.recipevault.utils.LogUtils
import com.nidhi.recipevault.domain.model.Recipe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipeListActivity : AppCompatActivity() {

    private val recipeViewModel: RecipeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LogUtils.getTag(this::class.java), "MainActivity class is creating")
        setContent {
            // Call the recipe list screen composable here
            RecipeListScreen(viewModel = recipeViewModel)
        }
    }
}