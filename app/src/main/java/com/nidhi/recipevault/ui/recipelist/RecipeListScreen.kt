package com.nidhi.recipevault.com.nidhi.recipevault.ui.recipelist

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nidhi.recipevault.R
import com.nidhi.recipevault.com.nidhi.recipevault.ui.theme.RecipeVaultTheme
import com.nidhi.recipevault.com.nidhi.recipevault.ui.theme.md_theme_light_semi_transparent_black

@Composable
fun RecipeListScreen(modifier: Modifier = Modifier) {
    Column(modifier) {
        Spacer(Modifier.height(16.dp))
        ScreenHeaderTitleSection(title = R.string.recipe_list_screen_title)
        Spacer(Modifier.height(16.dp))
        ScreenBodySection {
            RecipesLazyVerticalGrid()
        }
    }
}

// Step: Header section
@Composable
fun ScreenHeaderTitleSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
    }
}

// Step: Body section
@Composable
fun ScreenBodySection(
    modifier: Modifier = Modifier,
    @StringRes title: Int? = null,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        title?.let {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp)
            )
        }
        content()
    }
}
@Composable
fun RecipeItemCard(
    @DrawableRes drawable: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.
        cardElevation(defaultElevation = 10.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(drawable),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp
                ),
                color = md_theme_light_semi_transparent_black,
                content = {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                top = 4.dp,
                                end = 4.dp,
                                bottom = 4.dp
                            )
                            .wrapContentWidth()
                    )
                }
            )
        }
    }
}

@Composable
fun RecipesLazyVerticalGrid(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        modifier = modifier
    ) {
        items(recipesData) { item ->
            RecipeItemCard(item.drawable, item.text)
        }
    }
}

private val recipesData = listOf(
    R.drawable.khandvi to "Khandvi",
    R.drawable.milk_masala to "Milk Masala",
    R.drawable.spinach_rice_and_veges to "Spinach Rice with Vegetables in Paprika Sauce",
    R.drawable.mango_faluda to "Mango Faluda",
    R.drawable.handvo to "Handvo",
    R.drawable.vegetable_paniyaram to "Vegetable Paniyaram"
).map { DrawableStringPair(it.first, it.second) }
private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    val text: String
)

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun ScreenHeaderTitleSectionPreview() {
    RecipeVaultTheme { ScreenHeaderTitleSection(R.string.recipe_list_screen_title) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun RecipeItemCardPreview() {
    RecipeVaultTheme {
        RecipeItemCard(
            drawable = R.drawable.milk_masala,
            text = "Spinach Rice with Vegetables in Paprika Sauce",
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun RecipeListScreenPreview() {
    RecipeVaultTheme { RecipeListScreen() }
}
