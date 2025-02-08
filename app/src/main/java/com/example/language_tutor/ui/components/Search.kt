// ui/components/Search.kt
package com.example.language_tutor.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(modifier: Modifier) {
    var searchText by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    val suggestions = (1..100).map { "Курс $it" }
    val filteredSuggestions = if (searchText.isNotEmpty()) {
        suggestions.filter { it.contains(searchText, ignoreCase = true) }
    } else {
        suggestions
    }

    Box(modifier.fillMaxWidth()) {
        DockedSearchBar(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.Center),
            query = searchText,
            onQueryChange = { searchText = it },
            onSearch = { isActive = false },
            active = isActive,
            onActiveChange = { isActive = it },
            placeholder = { Text("Поиск курсов...") },
            leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = "Clear Icon",
                    modifier = Modifier.clickable {
                        searchText = ""
                        isActive = false
                    }
                )
            }
        ) {
            AnimatedVisibility(visible = isActive) {
                SearchSuggestions(
                    suggestions = filteredSuggestions,
                    onSuggestionClick = { resultText ->
                        searchText = resultText
                        isActive = false
                    }
                )
            }
        }
    }
}