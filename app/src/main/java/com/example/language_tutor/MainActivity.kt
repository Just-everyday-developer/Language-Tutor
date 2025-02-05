package com.example.language_tutor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.language_tutor.ui.theme.LanguageTutorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LanguageTutorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LanguageTutorTheme {
        App(Modifier)
    }
}

@Composable
fun App(modifier: Modifier) {
    Column {
        Header(Modifier)
        Search(Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(modifier: Modifier) {
    TopAppBar(
        title = { Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) { Text("Каталог") } }
    )
}}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(modifier: Modifier) {
    var searchText by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    val suggestions = (1..100).map { "Курс $it" }
    val filteredSuggestions = if (searchText.isNotEmpty()) {
        suggestions.filter { it.contains(searchText, ignoreCase = true) }
    } else { suggestions }

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
            },
        ) {
            AnimatedVisibility(visible = isActive)
            {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(filteredSuggestions) { resultText ->
                        ListItem(
                            modifier = Modifier
                                .clickable {
                                    searchText = resultText
                                    isActive = false
                                },
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
                            },
                        )
                    }
                }
            }
        }
    }
}

