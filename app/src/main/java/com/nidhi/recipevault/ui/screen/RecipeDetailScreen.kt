package com.nidhi.recipevault.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.nidhi.recipevault.R

@Composable
fun RecipeDetailScreen(
    recipeImage: Painter,
    recipeTitle: String,
    recipeDescription: String,
    cookTime: String,
    prepTime: String,
    difficultyLevel: String,
    mealType: String,
    cuisine: String,
    ingredients: List<String>,
    methodSteps: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .verticalScroll(rememberScrollState())
    ) {

        RecipeImage(image = recipeImage)
        Spacer(modifier = Modifier.height(16.dp))
        RecipeTitle(title = recipeTitle)
        RecipeDescription(description = recipeDescription)
        CookingTimeDisplay(cookTime = cookTime, prepTime = prepTime)
        Spacer(modifier = Modifier.height(16.dp))
        TagsRow(difficultyLevel, mealType, cuisine)
        Spacer(modifier = Modifier.height(16.dp))
        ToggleContentCard(
            ingredients = ingredients,
            methodSteps = methodSteps
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RecipeImage(image: Painter) {
    Image(
        painter = image,
        contentDescription = "Recipe Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun RecipeTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun RecipeDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun CookingTimeDisplay(cookTime: String, prepTime: String) {
    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = stringResource(R.string.cook_time, cookTime), style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.width(16.dp))
        VerticalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier
                .width(1.dp)
                .height(16.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = stringResource(R.string.prep_time, prepTime), style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun TagsRow(difficulty: String, mealType: String, cuisine: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TagItem(text = difficulty, color = MaterialTheme.colorScheme.primary)
        TagItem(text = mealType, color = MaterialTheme.colorScheme.primary)
        TagItem(text = cuisine, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun TagItem(text: String, color: Color) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(text = text, color = Color.Black, fontSize = 12.sp)
    }
}

@Composable
fun ToggleContentCard(
    ingredients: List<String>,
    methodSteps: List<String>
) {
    // State to manage selected section
    var showIngredients by remember { mutableStateOf(true) }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column {
            // Toggle Buttons
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { showIngredients = true },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (showIngredients) MaterialTheme.colorScheme.primary else Color.Transparent
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (!showIngredients) MaterialTheme.colorScheme.primary else Color.LightGray
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Ingredients",
                            color = if (showIngredients) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = { showIngredients = false },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (!showIngredients) MaterialTheme.colorScheme.primary else Color.Transparent
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (!showIngredients) MaterialTheme.colorScheme.primary else Color.LightGray
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Method",
                            color = if (!showIngredients) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }


            // Content (Ingredients or Method)
            Column(modifier = Modifier.padding(16.dp)) {
                val items = if (showIngredients) ingredients else methodSteps
                items.forEachIndexed { index, item ->
                    Text(
                        text = "${index + 1}. $item",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun RecipeDetailScreenPreview() {
    RecipeDetailScreen(
        recipeImage = painterResource(id = R.drawable.ic_milk_masala), // Replace with your drawable resource
        recipeTitle = "Spaghetti Carbonara",
        recipeDescription = "A classic Italian pasta dish made with eggs, cheese, pancetta, and pepper.",
        cookTime = "20 mins",
        prepTime = "10 mins",
        difficultyLevel = "Medium",
        mealType = "Dinner",
        cuisine = "Italian",
        ingredients = listOf(
            "200g Spaghetti",
            "100g Pancetta",
            "2 large eggs",
            "50g Pecorino cheese",
            "Salt",
            "Pepper"
        ),
        methodSteps = listOf(
            "Boil the spaghetti in salted water.",
            "Fry the pancetta until crispy.",
            "Whisk the eggs and cheese together.",
            "Combine pasta with pancetta and egg mixture.",
            "Serve with extra cheese and pepper."
        )
    )
}