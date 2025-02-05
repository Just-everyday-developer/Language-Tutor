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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.language_tutor.ui.theme.LanguageTutorTheme
import com.gowtham.ratingbar.RatingBar

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
        Search(Modifier.padding(bottom = 20.dp))
        Courses(Modifier)
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

@Composable
fun RatingStar(
    rating: Float = 5f,
    maxRating: Int = 5,
    onStarClick: (Int) -> Unit,
    isIndicator: Boolean = false
) {
    Row {
        for (i in 1..maxRating) {
            if (i <= rating.toInt()) {
                // Full stars
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(!isIndicator) {
                            onStarClick(i)
                        }
                )
            } else if (i == rating.toInt() + 1 && rating % 1 != 0f) {
                // Partial star
                PartialStar(fraction = rating % 1)
            } else {
                // Empty stars
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(!isIndicator) {
                            onStarClick(i)
                        }
                )
            }
        }
    }
}
@Composable
private fun PartialStar(fraction: Float) {
    val customShape = FractionalClipShape(fraction)

    Box {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Box(
            modifier = Modifier
                .graphicsLayer (
                    clip = true,
                    shape = customShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


private class FractionalClipShape(private val fraction: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(
            rect = Rect(
                left = 0f,
                top = 0f,
                right = size.width * fraction,
                bottom = size.height
            )
        )
    }
}

@Composable
fun Courses(modifier: Modifier) {
    val coursesList = listOf(
        "Английский для начинающих",
        "Английский для продолжающих",
        "Подготовка к IELTS"
    )
    val prices = listOf("Бесплатно", "60$", "180$")
    val photos = listOf(R.drawable.for_newbies, R.drawable.for_intermediaters, R.drawable.for_ielts)
    LazyColumn(modifier.fillMaxSize()) {
        items(listOf(0, 1, 2)) { index ->
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = modifier.padding(15.dp).height(170.dp).fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            ) {
                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = modifier.width(120.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = photos[index]),
                            contentDescription = "Изображение курса для начинающих",
                            modifier = modifier
                                .size(100.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .padding(bottom = 10.dp)
                        )
                        Text(
                            text = prices[index],
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = coursesList[index],
                            style = MaterialTheme.typography.titleMedium
                        )
                        var rating by remember { mutableFloatStateOf(3.5f) }
                        var isAvailable by remember { mutableStateOf(false) }
                        RatingStar(
                            rating = rating,
                            maxRating = 5,
                            onStarClick = { clickedStar ->
                                rating = clickedStar.toFloat()
                                isAvailable = !isAvailable
                        }, isAvailable)
                    }
                }
            }
        }
    }
}

