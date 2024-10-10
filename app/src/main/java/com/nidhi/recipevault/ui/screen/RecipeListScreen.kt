package com.nidhi.recipevault.com.nidhi.recipevault.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.nidhi.recipevault.R
import com.nidhi.recipevault.com.nidhi.recipevault.ui.composable.NoDataScreen
import com.nidhi.recipevault.com.nidhi.recipevault.ui.viewmodel.RecipeViewModel
import com.nidhi.recipevault.ui.composable.ErrorScreen
import com.nidhi.recipevault.ui.composable.ItemCard
import com.nidhi.recipevault.ui.composable.LazyVerticalItemGrid
import com.nidhi.recipevault.ui.composable.LoadingScreen
import com.nidhi.recipevault.ui.composable.ScreenHeaderTitleSection
import com.nidhi.recipevault.ui.state.RecipeUiState

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel
) {
    val uiState by viewModel.recipesState.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        // Header Section
        ScreenHeaderTitleSection(title = R.string.recipe_list_screen_title) // Set your header title here
        // Body Section
        when (uiState) {
            is RecipeUiState.Loading -> {
                LoadingScreen()
            }
            is RecipeUiState.Success -> {
                val recipes = (uiState as RecipeUiState.Success).recipes
                LazyVerticalItemGrid(
                    recipes,
                    itemContent = { item ->
                        ItemCard(
                            thumbnailName = item.thumbnail,
                            title = item.name
                        )
                    }
                )
            }
            is RecipeUiState.Error -> {
                val message = (uiState as RecipeUiState.Error).message
                ErrorScreen(message = message)
            }

            is RecipeUiState.NoRecipes -> {
                NoDataScreen(R.string.no_recipe_found)
            }
        }
    }
}
