package com.nidhi.recipevault.ui.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nidhi.recipevault.R
import com.nidhi.recipevault.com.nidhi.recipevault.ui.theme.RecipeVaultTheme

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
@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun ScreenHeaderTitleSectionPreview() {
    RecipeVaultTheme {
        ScreenHeaderTitleSection(R.string.recipe_list_screen_title) }
}