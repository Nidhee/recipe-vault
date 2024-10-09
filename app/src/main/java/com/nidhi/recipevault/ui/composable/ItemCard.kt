package com.nidhi.recipevault.ui.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nidhi.recipevault.R
import com.nidhi.recipevault.com.nidhi.recipevault.ui.theme.RecipeVaultTheme
import com.nidhi.recipevault.com.nidhi.recipevault.ui.theme.md_theme_light_semi_transparent_black
import com.nidhi.recipevault.com.nidhi.recipevault.utils.getDrawableId

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    thumbnailName: String?,
    title: String,
    content: @Composable (() -> Unit)? = null, // Optional content for more flexibility
    @DrawableRes defaultDrawable: Int  = R.drawable.ic_default_thumbnail // Default drawable resource ID
) {
    val context = LocalContext.current
    val drawableId = if (!thumbnailName.isNullOrEmpty()) {
        context.getDrawableId(thumbnailName) // Retrieve the drawable by name
    } else {
        defaultDrawable // Use default drawable if thumbnailName is null or empty
    }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
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
                painter = painterResource(drawableId),
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
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                top = 4.dp,
                                end = 4.dp,
                                bottom = 4.dp
                            )
                            .wrapContentWidth()
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                        content?.invoke() // Render optional content if provided
                    }
                }
            )
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun ItemCardPreview() {
    RecipeVaultTheme {
        ItemCard(
            thumbnailName = "ic_milk_masala",
            title = "Spinach Rice with Vegetables in Paprika Sauce",
            modifier = Modifier.padding(8.dp)
        )
    }
}