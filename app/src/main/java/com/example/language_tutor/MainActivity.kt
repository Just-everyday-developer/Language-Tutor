package com.example.language_tutor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.language_tutor.ui.theme.LanguageTutorTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LanguageTutorTheme {
                App()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        LanguageTutorTheme {
            App()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = "Меню",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(24.dp),
                    fontWeight = FontWeight.Bold
                )
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )

                // Улучшенные элементы меню
                DrawerItem(text = "Главная", icon = Icons.Filled.Menu) { scope.launch { drawerState.close() } }
                DrawerItem(text = "Моя статистика", icon = Icons.Rounded.Star) { scope.launch { drawerState.close() } }
                DrawerItem(text = "Экзамен", icon = Icons.Filled.Star) { scope.launch { drawerState.close() } }
                DrawerItem(text = "Запоминатор", icon = Icons.Rounded.Search) { scope.launch { drawerState.close() } }
            }
        }
    ) {
        Scaffold(
            topBar = {
                Header(
                    title = "Каталог курсов",
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Search(Modifier.padding(bottom = 20.dp))
                Courses(Modifier)
            }
        }
    }
}

@Composable
fun DrawerItem(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        selected = false,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = text
            )
        },
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    title: String,
    onMenuClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Открыть меню"
                )
            }
        }
    )
}

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
            },
        ) {
            AnimatedVisibility(visible = isActive) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(filteredSuggestions) { resultText ->
                        ListItem(
                            modifier = Modifier.clickable {
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
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(enabled = !isIndicator) { onStarClick(i) }
                )
            } else if (i == rating.toInt() + 1 && rating % 1 != 0f) {
                PartialStar(fraction = rating % 1)
            } else {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(enabled = !isIndicator) { onStarClick(i) }
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
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Box(
            modifier = Modifier
                .graphicsLayer(
                    clip = true,
                    shape = customShape
                )
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
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
    val photos = listOf(
        R.drawable.for_newbies,
        R.drawable.for_intermediaters,
        R.drawable.for_ielts
    )

    LazyColumn(modifier.fillMaxSize()) {
        items(listOf(0, 1, 2)) { index ->
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier
                    .padding(15.dp)
                    .height(170.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.width(120.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = photos[index]),
                            contentDescription = "Изображение курса",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
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
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = coursesList[index],
                            style = MaterialTheme.typography.titleMedium
                        )
                        // Каждая карточка имеет собственное состояние рейтинга
                        var rating by remember { mutableFloatStateOf(3.5f) }
                        RatingStar(
                            rating = rating,
                            maxRating = 5,
                            onStarClick = { clickedStar ->
                                rating = clickedStar.toFloat()
                            },
                            isIndicator = false
                        )
                    }
                }
            }
        }
    }
}
