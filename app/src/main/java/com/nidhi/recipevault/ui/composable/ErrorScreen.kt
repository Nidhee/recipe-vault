package com.nidhi.recipevault.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nidhi.recipevault.com.nidhi.recipevault.ui.theme.RecipeVaultTheme

@Composable
fun ErrorScreen(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(start = 16.dp, end = 16.dp)
    )
}
@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun ErrorScreenPreview() {
    RecipeVaultTheme {
        ErrorScreen("Error fetching recipes")
    }
}