// ui/components/SearchSuggestions.kt
package com.example.language_tutor.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement



@Composable
fun SearchSuggestions(
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(suggestions) { resultText ->
            ListItem(
                modifier = Modifier.clickable { onSuggestionClick(resultText) },
                headlineContent = {
                    Text(
                        text = resultText,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                leadingContent = {
                    Icon(
                        Icons.Rounded.Star,
                        contentDescription = "Favourite suggestions"
                    )
                }
            )
        }
    }
}