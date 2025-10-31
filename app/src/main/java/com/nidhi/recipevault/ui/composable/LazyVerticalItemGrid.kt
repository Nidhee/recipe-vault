package com.nidhi.recipevault.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nidhi.recipevault.ui.theme.RecipeVaultTheme

@Composable
fun <T> LazyVerticalItemGrid(
    items: List<T>,
    itemContent: @Composable (T) -> Unit, // A composable lambda to define how to display each item
    modifier: Modifier = Modifier,
    columns: Int = 2 // Allow users to define how many columns they want
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp,
            end = 16.dp,
            bottom = 16.dp // Add bottom padding to give space below the last row
        )
    ) {
        items(items) { item ->
            itemContent(item) // Use the provided lambda to render the item
        }
    }
}

private val dummyRecipeData = listOf(
   "ic_khandvi" to "Khandvi",
   "ic_milk_masala" to "Milk Masala",
   "ic_spinach_rice_and_veges" to "Spinach Rice with Vegetables in Paprika Sauce",
   "ic_mango_faluda" to "Mango Faluda",
   "ic_handvo" to "Handvo",
   "ic_vegetable_paniyaram" to "Vegetable Paniyaram",
   "ic_dahi_bhindi" to "Dahi Bhindi",
    "" to "Dal Dhokadi"
).map { DummyRecipeData(it.first, it.second) }

private data class DummyRecipeData(
    val thumbnailName: String,
    val text: String
)

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun LazyVerticalItemGridPreview() {
    RecipeVaultTheme {
        LazyVerticalItemGrid(
            dummyRecipeData,
            itemContent = { item ->
                ItemCard(
                    thumbnailName = item.thumbnailName,
                    title = item.text
                )
            }
        )
    }
}